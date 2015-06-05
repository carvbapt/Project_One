package com.android.exemplo.projectone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.android.exemplo.projectone.helper.SessionManager;

public class splashActivity extends Activity {
    private SessionManager session;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    String username,password;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Session manager
        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(splashActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

        final String previousConfigSortCode[] = (String[]) getLastNonConfigurationInstance();
        if (previousConfigSortCode!=null) {
            username = previousConfigSortCode[1];
            password = previousConfigSortCode[2];
        }
        showLoginDialog();
    }



    public void showLoginDialog()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View prompt = li.inflate(R.layout.login_dialog, null);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);

        final EditText
                user = (EditText) prompt.findViewById(R.id.login_name);
        final EditText
                pass = (EditText) prompt.findViewById(R.id.login_password);
        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)

        user.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                username = user.getText().toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {  }
        });

        pass.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                password = pass.getText().toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        final String previousConfigSortCode[] = (String[]) getLastNonConfigurationInstance();
        if (previousConfigSortCode!=null){
            user.setText(username);
            pass.setText(password);
        }


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
                                    session.setLogin(true);
                                    Intent loginPage = new Intent(splashActivity.this, DashBoardActivity.class);
                                    startActivity(loginPage);
                                    finish();
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
        final String sortcode[] = {"rotate", username, password};
        return sortcode;
    }

}

