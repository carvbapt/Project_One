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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
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

    TextView txt_agdata;

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
        txt_agdata = (TextView) findViewById(R.id.txt_agdata);
        txt_agdata.setText(message);

        // listar items da base de dados
        list_c = buildData_c();
        list_f = buildData_f();
        from_c = new String[2];


        to_c = new int[2];

        if (th_ag.getCurrentTab() == 0) {

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
                    from_c[1]="empresa";
                    to_c[0]=R.id.tv_agcom;
                    to_c[1]=R.id.tv_agdata;
                    lv_c = (ListView) findViewById(R.id.lv_agcom);
                    adapter_c = new SimpleAdapter(AgDetalActivity.this,list_c,R.layout.activity_agcomrow, from_c, to_c);
                    lv_c.setAdapter(adapter_c);

                } else if (th_ag.getCurrentTab() == 1) {
//                    tv_agtab2.setText(message + " Financeiro");
                    from_c[0]="financeiro";
                    from_c[1]="valor";
                    to_c[0]=R.id.tv_agcom;
                    to_c[1]=R.id.tv_agdata;
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

        Arrays.sort(Dados.com_empresa, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[2];
                final String time2 = entry2[2];
                return time1.compareTo(time2);
            }
        });

        for (final String[] s : Dados.com_empresa) {
//            Log.i("","linha2 -  "+s[0] + " " + s[1]+ " "+ s[2]);
        }


        int i=0;
        for(i=0; i<Dados.com_empresa.length;i++) {
            int ind=Integer.parseInt(Dados.com_empresa[i][0]);
            list.add(putData(Dados.Comerciais[parseInt(Dados.com_empresa[i][1])], Dados.Empresas[ind].substring(0, (Dados.Empresas[ind].length() >= 22) ? 22 : Dados.Empresas[ind].length()) + ((Dados.Empresas[ind].length() >= 22) ? "..." : "")));
        }

        return list;
    }

    private HashMap<String, String> putData(String comercial, String data) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("comercial", comercial);
        item.put("empresa", data);
        return item;
    }

    private ArrayList<Map<String, String>> buildData_f() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

        List<Ag_Comercial> comList;

       String[][] array_fin= new String[Dados.fin_empresa.length][3];
       int count=0;
       Date date;
       SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        Arrays.sort(Dados.fin_empresa, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[2];
                final String time2 = entry2[2];
                return time1.compareTo(time2);
            }
        });

        for (final String[] s : Dados.fin_empresa) {
            try {
                date=formatter.parse(s[2]);

                //            if(formatter.format(date).equals(message)) {
                array_fin[count][0] = s[0];
                array_fin[count][1] = s[1];
                array_fin[count][2] = s[2];
                count++;
//            }
                Log.i("", "linha S -  " + s[0] + " " + s[1] + " " +date +"->" +message );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i<count;i++) {

            float val=Float.parseFloat(array_fin[i][1]);
            int ind=Integer.parseInt(array_fin[i][0]);
            list.add(putData2(Dados.Empresas[ind].substring(0, (Dados.Empresas[ind].length() >= 22) ? 22 : Dados.Empresas[ind].length()) + ((Dados.Empresas[ind].length() >= 22) ? "..." : ""), String.format("%.2f", val ) +"€"));
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
