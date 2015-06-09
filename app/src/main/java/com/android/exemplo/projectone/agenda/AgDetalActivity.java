package com.android.exemplo.projectone.agenda;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.exemplo.projectone.EmpresaActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Base_Activity;
import com.android.exemplo.projectone.helper.Dados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.*;

public class AgDetalActivity extends Base_Activity {

    // TABULADOR DETALHES
    String message;
    TabHost th_ag;
    TabHost.TabSpec tspe_ag;
    int ind;

    TextView tv_agtab1, tv_agtab2;

    ArrayList<Map<String, String>> list_c;
    String[] from_c;
    int[] to_c;
    // List view
    private ListView lv_c;
    // Adapter
    SimpleAdapter adapter_c;

    ArrayList<Map<String, String>> list_f;
    String[] from_f;
    int[] to_f;
    // List view
    private ListView lv_f;
    // Adapter
    SimpleAdapter adapter_f;



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

        // listar items da base de dados
        list_c = buildData_c();
        list_f = buildData_f();
        from_c = new String[2];


        to_c = new int[2];

        if (th_ag.getCurrentTab() == 0) {
            tv_agtab1.setText(message + " Comercial");
            from_c[0]="comercial";
            from_c[1]="data";
            to_c[0]=R.id.tv_agcom;
            to_c[1]=R.id.tv_agdata;

            lv_c = (ListView) findViewById(R.id.lv_agcom);
            adapter_c = new SimpleAdapter(this,list_c,R.layout.activity_agcomrow, from_c, to_c);
            lv_c.setAdapter(adapter_c);
        }

        th_ag.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (th_ag.getCurrentTab() == 0) {
                    from_c[0]="comercial";
                    from_c[1]="data";
                    to_c[0]=R.id.tv_agcom;
                    to_c[1]=R.id.tv_agdata;
                    tv_agtab1.setText(message + " Comercial");
                    lv_c = (ListView) findViewById(R.id.lv_agcom);
                    adapter_c = new SimpleAdapter(AgDetalActivity.this,list_c,R.layout.activity_agcomrow, from_c, to_c);
                    lv_c.setAdapter(adapter_c);

                } else if (th_ag.getCurrentTab() == 1) {
                    tv_agtab2.setText(message + " Financeiro");
                    from_c[0]="financeiro";
                    from_c[1]="valor";
                    to_c[0]=R.id.tv_agcom;
                    to_c[1]=R.id.tv_agdata;
                    tv_agtab2.setText(message + " Financeiro");
                    lv_f = (ListView) findViewById(R.id.lv_agfin);
                    adapter_f = new SimpleAdapter(AgDetalActivity.this,list_f,R.layout.activity_agcomrow, from_c, to_c);
                    lv_f.setAdapter(adapter_f);
                }
            }
        });
    }

    private ArrayList<Map<String, String>> buildData_c() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

        List<Ag_Comercial> comList;

        int i=0;
        for(i=0; i<Dados.com_empresa.length;i++) {
            list.add(putData(Dados.Comerciais[parseInt(Dados.com_empresa[i][1])], Dados.com_empresa[i][2]));
        }

        return list;
    }

    private HashMap<String, String> putData(String comercial, String data) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("comercial", comercial);
        item.put("data", data);
        return item;
    }

    private ArrayList<Map<String, String>> buildData_f() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

        List<Ag_Comercial> comList;

        int i=0;
        for(i=0; i<Dados.fin_empresa.length;i++) {
            list.add(putData2(Dados.Empresas[parseInt(Dados.com_empresa[i][0])].substring(0, (Dados.Empresas[parseInt(Dados.com_empresa[i][0])].length() >= 32) ? 32 : Dados.Empresas[parseInt(Dados.com_empresa[i][0])].length()) + ((Dados.Empresas[parseInt(Dados.com_empresa[i][0])].length() >= 32) ? "..." : ""), Dados.fin_empresa[i][1]));
        }

        return list;
    }

    private HashMap<String, String> putData2(String financeiro, String valor) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("financeiro", financeiro);
        item.put("valor", valor);
        return item;
    }

}
