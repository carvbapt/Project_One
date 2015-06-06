package com.android.exemplo.projectone.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.exemplo.projectone.helper.Dados;
import com.android.exemplo.projectone.EmpresaActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Base_Activity;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class EmpDetalActivity extends Base_Activity {


    String message;
    TabHost tabhost;
    TabHost.TabSpec tabspe;
    int ind;
    TextView detmorada, detlocal, dettlf, detrepre, detdtmanut;

    Dados dados;

    ListView list;
    String[] com_nome;
    String[] com_data;
    String com_aux;
    EmpAdapter adapter;
    StringBuilder str_dt;
    String ch, aux;
    ImageButton bt_comdetal;

    Date datai, dataf;
    SimpleDateFormat sdf;

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
        Log.i("", "MSG-" + message);
        ind = -1;
        for (int i = 0; i <= Dados.Empresas.length; i++) {
            if (Dados.Empresas[i].equals(message)) {
                ind = i;
                break;
            }
        }

        if (tabhost.getCurrentTab() == 0) {
            load_comercial(ind);
        }
        // Create the text view
//        TextView textView = (TextView) findViewById(R.id.txt_detnome);
//        textView.setTextSize(15);
//        textView.setText(Dados.Empresas[ind].substring(0, (Dados.Empresas[ind].length() >= 35) ? 30 : Dados.Empresas[ind].length()) + "...");

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


//    public void onClickBT(View v) {
//        Toast.makeText(this,"Mail para.... ", Toast.LENGTH_SHORT).show();
//    }

    public void load_comercial(int ind) {
//        Toast.makeText(EmpDetalActivity.this, "Index" + ind + "\nEmpresa " + Dados.com_empresa[ind], Toast.LENGTH_SHORT).show();

        int i, count = 0, z = 0;
        bt_comdetal = (ImageButton) findViewById(R.id.BT_comail);

        list = (ListView) findViewById(R.id.LVempdetal);

        // Tamanho do Array
        for (i = 0; i < Dados.com_empresa.length; i++) {
            if (Dados.com_empresa[i][0].equals("" + ind)) {
                count++;
            }
        }

        com_nome = new String[count];
        com_data = new String[count];
        int zz = 0;

        for (i = 0; i < Dados.com_empresa.length; i++) {
            if (Dados.com_empresa[i][0].equals("" + ind)) {
//                aux = Dados.Comerciais[Integer.parseInt(Dados.com_empresa[i][1])];
//                com_nome[z] = aux.substring(0, (aux.length() >= 21) ? 20 : aux.length()) + "...";
                com_nome[z] = Dados.Comerciais[Integer.parseInt(Dados.com_empresa[i][1])];
//                str_dt = new StringBuilder(Dados.com_empresa[i][2]);
//                ch = "-";
//                str_dt.insert(2, ch);
//                str_dt.insert(5, ch);
                com_data[z] = Dados.com_empresa[i][2];

//                if(z>0) {
//                        sdf = new SimpleDateFormat("dd-MM-yyyy");
//                    try {
//                        dataf = sdf.parse(com_data[z]);
//                        datai=sdf.parse(com_data[zz]);
//                        while (zz>0){
//
//                            if (datai.before(dataf)) {
//                                com_aux = com_data[zz - 1];
//                                com_data[zz - 1] = com_data[zz];
//                                com_data[zz] = com_aux;
////                                com_aux = com_nome[zz - 1];
////                                com_nome[zz - 1] = com_nome[zz];
////                                com_nome[zz] = com_aux;
//                                zz--;
//                            }
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }

                Log.i("", "DAD_emp-" + com_nome[z] + " - " + com_data[z] + " " + com_nome.length);
                z++;
                zz = z;

            }
        }
        adapter = new EmpAdapter(getApplicationContext(), R.layout.activity_emprow);
        list.setAdapter(adapter);
        if (!com_nome.equals("")) {
            for (i = 0; i < com_nome.length; i++) {
                EmpDataProvider dataProvider = new EmpDataProvider(com_nome[i], com_data[i]);
                adapter.add(dataProvider);
            }
        } else {
            Toast.makeText(EmpDetalActivity.this, "Comercial não tem Empresas atribuidas", Toast.LENGTH_SHORT).show();
        }

//        bt_comdetal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(EmpDetalActivity.this,"Mail para.... ", Toast.LENGTH_SHORT).show();
//            }
//        });

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
//                str_dt.insert(2, ch);
//                str_dt.insert(5, ch);
                detdtmanut.setText(str_dt);
                break;
            }
        }

        //   Toast.makeText(EmpDetalActivity.this, "Tabulado   etalhe\nposição " + ind, Toast.LENGTH_SHORT).show();
    }
}
