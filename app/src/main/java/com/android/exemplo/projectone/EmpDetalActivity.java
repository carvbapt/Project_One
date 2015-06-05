package com.android.exemplo.projectone;

import android.app.Activity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class EmpDetalActivity extends AppCompatActivity {


    String message;
    TabHost tabhost;
    TabHost.TabSpec tabspe;
    int ind;
    TextView detmorada, detlocal, dettlf, detrepre, detdtmanut;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empdetal);

        // Criar Tabs na actividade
        tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();
        tabspe = tabhost.newTabSpec("Comercial");
        tabspe.setContent((R.id.tab_com));
        tabspe.setIndicator("Comercial");
        tabhost.addTab(tabspe);
        tabspe = tabhost.newTabSpec("Financeiro");
        tabspe.setContent((R.id.tab_fin));
        tabspe.setIndicator("Financeiro");
        tabhost.addTab(tabspe);
        tabspe = tabhost.newTabSpec("Detalhe");
        tabspe.setContent((R.id.tab_det));
        tabspe.setIndicator("Detalhe");
        tabhost.addTab(tabspe);

        // Get the message from the intent
        Intent intent = getIntent();
        message = intent.getStringExtra(EmpresaActivity.EXTRA_MESSAGE);
        Log.i("", message);
        ind = -1;
        for (int i = 0; i <= Dados.Empresas.length; i++) {
            if (Dados.Empresas[i].equals(message)) {
                ind = i;
                break;
            }
        }

        // Create the text view
        TextView textView = (TextView) findViewById(R.id.txt_detnome);
        textView.setTextSize(15);
        textView.setText(Dados.Empresas[ind].substring(0, (Dados.Empresas[ind].length() >= 35) ? 30 : Dados.Empresas[ind].length()) + "...");

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabhost.getCurrentTab() == 0) {
                    load_comercial(ind);
                } else if (tabhost.getCurrentTab() == 1) {
                    load_financeiro(ind);
                } else if (tabhost.getCurrentTab() == 2) {
                    load_detalhe(ind);
                }
            }
        });
    }

    public void load_comercial(int ind) {
        Toast.makeText(EmpDetalActivity.this, "Tabulado Comercial\n" + ind, Toast.LENGTH_SHORT).show();
    }

    public void load_financeiro(int ind) {
        Toast.makeText(EmpDetalActivity.this, "Tabulado Financeiro\n" + ind, Toast.LENGTH_SHORT).show();
    }

    public void load_detalhe(int ind) {

        Dados ldados = new Dados();

        detmorada = (TextView) findViewById(R.id.txt_detmorada);
        detlocal = (TextView) findViewById(R.id.txt_detlocalidade);
        dettlf = (TextView) findViewById(R.id.txt_dettelefone);
        detrepre = (TextView) findViewById(R.id.txt_detrepres);
        detdtmanut = (TextView) findViewById(R.id.txt_detdtmanut);

        for (int i = 0; i < Dados.det_empresa.length; i++) {
            if (Dados.det_empresa[i][0].equals("" + ind)) {
                detmorada.setText(Dados.det_empresa[i][1]);
                detlocal.setText(Dados.det_empresa[i][2]);
                dettlf.setText(Dados.det_empresa[i][3]);
                detrepre.setText(Dados.det_empresa[i][4]);
                detdtmanut.setText(Dados.det_empresa[i][5]);
                break;
            }
        }

        Toast.makeText(EmpDetalActivity.this, "Tabulado   etalhe\nposição " + ind, Toast.LENGTH_SHORT).show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empdetal, menu);
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
