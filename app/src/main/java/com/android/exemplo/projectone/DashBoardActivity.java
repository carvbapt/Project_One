package com.android.exemplo.projectone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.exemplo.projectone.helper.Base_Activity;
import com.android.exemplo.projectone.helper.SessionManager;


public class DashBoardActivity extends Base_Activity {
    Button BTtoagenda, BTtocomercial, BTtoempresa;
    ImageButton BTItoagenda, BTItocomercial, BTItoempresa;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        BTtoagenda=(Button)findViewById(R.id.BTtoagenda);
        BTtocomercial=(Button)findViewById(R.id.BTtocomercial);
        BTtoempresa=(Button)findViewById(R.id.BTtoempresa);


        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }


        BTtoagenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DashBoardActivity.this,AgendaActivity.class);
                startActivity(intent);
            }
        });

        BTtocomercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DashBoardActivity.this,ComerciaisActivity.class);
                startActivity(intent);
            }
        });

        BTtoempresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DashBoardActivity.this,EmpresaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_normal, menu);
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

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * */
    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(DashBoardActivity.this, splashActivity.class);
        startActivity(intent);
        finish();
    }
}
