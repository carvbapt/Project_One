package com.android.exemplo.projectone.helper;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.splashActivity;

/**
 * Created by tiagogb on 04/06/2015.
 */
public class Base_Activity extends AppCompatActivity {
    private SessionManager session;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_normal, menu);

        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        if (!hasConnectivity(getApplicationContext(), false)){
            logoutUser();
        }



        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mail:
                Toast.makeText(getApplicationContext(), "TGB: Email", Toast.LENGTH_SHORT).show();
//                openSearch();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "TGB: settings", Toast.LENGTH_SHORT).show();
                //  openSettings();
                return true;
            case R.id.action_logout:
//                Toast.makeText(getApplicationContext(), "TGB: logout", Toast.LENGTH_SHORT).show();
                // session manager
                session = new SessionManager(getApplicationContext());
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(this, splashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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

}
