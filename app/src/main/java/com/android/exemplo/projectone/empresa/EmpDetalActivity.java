package com.android.exemplo.projectone.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.exemplo.projectone.helper.Dados;
import com.android.exemplo.projectone.EmpresaActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Base_Activity;


public class EmpDetalActivity extends Base_Activity {


    String message;
    TabHost tabhost;
    TabHost.TabSpec tabspe;
    int ind;
    TextView detmorada, detlocal, dettlf, detrepre, detdtmanut;

    Dados dados;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empdetal);

        dados = new Dados();

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
        for (int i = 0; i <= dados.Empresas.length; i++) {
            if (dados.Empresas[i].equals(message)) {
                ind = i;
                break;
            }
        }

        // Create the text view
        TextView textView = (TextView) findViewById(R.id.txt_detnome);
        textView.setTextSize(15);
        textView.setText(dados.Empresas[ind].substring(0, (dados.Empresas[ind].length() >= 35) ? 30 : dados.Empresas[ind].length()) + "...");

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
//        Toast.makeText(EmpDetalActivity.this, "Tabulado Comercial\n" + ind, Toast.LENGTH_SHORT).show();
    }

    public void load_financeiro(int ind) {
//        Toast.makeText(EmpDetalActivity.this, "Tabulado Financeiro\n" + ind, Toast.LENGTH_SHORT).show();
    }

    public void load_detalhe(int ind) {

        StringBuilder str_dt;
        String ch = "-";

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

                str_dt = new StringBuilder(Dados.det_empresa[i][5]);
                str_dt.insert(2, ch);
                str_dt.insert(5, ch);
                detdtmanut.setText(str_dt);
                break;
            }
        }

        //   Toast.makeText(EmpDetalActivity.this, "Tabulado   etalhe\nposição " + ind, Toast.LENGTH_SHORT).show();


    }
}
