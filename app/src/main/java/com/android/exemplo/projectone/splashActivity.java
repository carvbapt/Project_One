package com.android.exemplo.projectone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class splashActivity extends Activity {

    Button BTtodash;

    private final int DURATION = 3000;
    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(DURATION);
                    } catch (InterruptedException e) {
                    } finally {
                        finish();
                        Intent intent = new Intent(getBaseContext(), DashBoardActivity.class);
                        startActivity(intent);
                    }
                }
            }
//            BTtodash=(Button)findViewById(R.id.BTtodash);
//
//        BTtodash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(splashActivity.this,DashBoardActivity.class);
//                startActivity(intent);
//
        };



        mSplashThread.start();
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
