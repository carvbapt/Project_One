package com.android.exemplo.projectone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class splashActivity extends Activity {

    private final int DURATION = 5000;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    EditText user,pass;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        user = (EditText)findViewById(R.id.login_name);
        pass = (EditText)findViewById(R.id.login_password);

        final String[] previousConfigSortCode = (String[]) getLastNonConfigurationInstance();

        if (previousConfigSortCode != null) {
            user.setText(previousConfigSortCode[0].toString());
            pass.setText(previousConfigSortCode[1].toString());
        }else {
        // Add delay so that Splash Screen is displayed for 3secs
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoginDialog();
            }
        }, DURATION);
        }
    }

    public void showLoginDialog()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View prompt = li.inflate(R.layout.login_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);

        final EditText user = (EditText) prompt.findViewById(R.id.login_name);
        final EditText pass = (EditText) prompt.findViewById(R.id.login_password);
        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)
        //pass.setText(Login_PASS);
        alertDialogBuilder.setTitle("LOGIN");
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (!hasConnectivity(splashActivity.this, false)) //check for internet connectivity
                        {
                            Toast.makeText(splashActivity.this, "No internet access... please connect.", Toast.LENGTH_LONG).show();
                            showLoginDialog();
                            return;
                        }
                        username = user.getText().toString();
                        password = pass.getText().toString();

                        try {
                            if (username.length() < 2 || password.length() < 2) {
                                Toast.makeText(splashActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                                showLoginDialog();
                            } else {
//                                 password = computeSHA1Hash(password); //password is hashed SHA1
                                //TODO here any local checks if password or user is valid

                                //this will do the actual check with my back-end server for valid user/pass and callback with the response
                                //new CheckLoginAsync(MainActivity.this,username,password).execute("","");
                                if (username.equals("Admin") && password.equals("Admin")) {
                                    Intent loginPage = new Intent(splashActivity.this, DashBoardActivity.class);
                                    startActivity(loginPage);
                                    showLoginDialog();
                                } else {
                                    Toast.makeText(splashActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                                    showLoginDialog();
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(splashActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();
            }
        });

        alertDialogBuilder.show();
        if (user.length()>1) //if we have the username saved then focus on password field, be user friendly :-)
            pass.requestFocus();
    }

    public static boolean hasConnectivity(Context context,boolean wifiOnly)
    {
        try
        {
            boolean haveConnectedWifi = false;
            boolean haveConnectedMobile = false;

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo)
            {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }

            if (wifiOnly)
                return haveConnectedWifi;
            else
                return haveConnectedWifi || haveConnectedMobile;

        }
        catch(Exception e)
        {
            return true; //just in case it fails move on, say yeah! we have Internet connection (hopefully)
        }

    }

    /**
     * SHA1 digest
     * @param stringToDigest
     * @return 40 len string
     */
    public static String computeSHA1Hash(String stringToDigest)
    {
        MessageDigest mdSha1;
        String SHAHash;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            return stringToDigest;
        }
        try {
            mdSha1.update(stringToDigest.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            return stringToDigest;
        }
        byte[] data = mdSha1.digest();
        SHAHash=bytesToHex(data);
        return SHAHash;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        final String sortcode[] = {
                username,password};
        return sortcode;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
