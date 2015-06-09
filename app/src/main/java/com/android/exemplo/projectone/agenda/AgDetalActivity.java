package com.android.exemplo.projectone.agenda;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.exemplo.projectone.EmpresaActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Base_Activity;
import com.android.exemplo.projectone.helper.Dados;

public class AgDetalActivity extends Base_Activity {

    // TABULADOR DETALHES
    String message;
    TabHost th_ag;
    TabHost.TabSpec tspe_ag;
    int ind;

    TextView tv_agtab1, tv_agtab2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agdetal);

        // Criar Tabs na actividade
        th_ag = (TabHost) findViewById(R.id.th_ag);
        th_ag.setup();
        tspe_ag = th_ag.newTabSpec("Comercial");
        tspe_ag.setContent((R.id.tab1_ag));
        tspe_ag.setIndicator("Comercial");
        th_ag.addTab(tspe_ag);
        tspe_ag = th_ag.newTabSpec("Financeiro");
        tspe_ag.setContent((R.id.tab2_ag));
        tspe_ag.setIndicator("Financeiro");
        th_ag.addTab(tspe_ag);

        // Get the message from the intent
        Intent intent = getIntent();
        message = intent.getExtras().getString("dia");
        Log.i("", "MSG-" + message);

        // Create the text view
        tv_agtab1 = (TextView) findViewById(R.id.tv_agtab1);
        tv_agtab2 = (TextView) findViewById(R.id.tv_agtab2);


        if (th_ag.getCurrentTab() == 0) {
            tv_agtab1.setText(message + " Comercial");
        }


        th_ag.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (th_ag.getCurrentTab() == 0) {
                    tv_agtab1.setText(message + " Comercial");

                } else if (th_ag.getCurrentTab() == 1) {
                    tv_agtab2.setText(message + " Financeiro");
                }
            }
        });
    }

}
