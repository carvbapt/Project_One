package com.android.exemplo.projectone.empresa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.exemplo.projectone.helper.Dados;
import com.android.exemplo.projectone.EmpresaActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Base_Activity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;


public class EmpDetalActivity extends Base_Activity {

    // TABULADOR DETALHES
    String message;
    TabHost tabhost;
    TabHost.TabSpec tabspe;
    int ind;
    TextView detmorada, detlocal, dettlf, detrepre, detdtmanut;
    Button btnMap;

    // TABULADOR COMERCIAIS
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

    //TABULADOR FINANCEIRO
    private BarChart b_Chart;
    private LineChart l_Chart;
    private float[] yData;
    private String[] xData;
    private LinearLayout tab_fin;
    private List<BarEntry> yVals;
    private List<Entry> yVals_l;
    private List<String> xVals;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empdetal);


        btnMap = (Button) findViewById(R.id.Btdetmapa);
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
        TextView textView = (TextView) findViewById(R.id.txt_detnome);
        textView.setTextSize(15);

        textView.setText(Dados.Empresas[ind].substring(0, (Dados.Empresas[ind].length() >= 32) ? 32 : Dados.Empresas[ind].length()) + ((Dados.Empresas[ind].length() >= 32) ? "..." : ""));



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


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpDetalActivity.this, MapsActivity.class);
                intent.putExtra("key", ""+ind); //Optional parameters
                startActivity(intent);
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

        // Recolha de dados para grafico
        int i, z = 0, count = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMaximumFractionDigits(2);
        float lmax, lmin;
        ;

        // Tamanho do Array
        for (i = 0; i < Dados.fin_empresa.length; i++) {
            if (Dados.fin_empresa[i][0].equals("" + ind)) {
                count++;
            }
        }
        xData = new String[count];
        yData = new float[count];

        xVals = new ArrayList<String>();
        yVals = new ArrayList<BarEntry>();
        yVals_l = new ArrayList<Entry>();

        lmin = lmax = 0;

        for (i = 0; i < Dados.fin_empresa.length; i++) {
            if (Dados.fin_empresa[i][0].equals("" + ind)) {
                xData[z] = Dados.fin_empresa[i][2];
                yData[z] = Float.parseFloat(df.format(Float.parseFloat("" + Dados.fin_empresa[i][1])));

                xVals.add(Dados.fin_empresa[i][2]);
                yVals.add(new BarEntry(Float.parseFloat("" + Dados.fin_empresa[i][1]), z));
                yVals_l.add(new Entry(Float.parseFloat("" + Dados.fin_empresa[i][1]), z));

                if (lmin > Float.parseFloat("" + Dados.fin_empresa[i][1])) {
                    lmin = Float.parseFloat("" + Dados.fin_empresa[i][1]);
                }
                if (lmax < Float.parseFloat("" + Dados.fin_empresa[i][1])) {
                    lmax = Float.parseFloat("" + Dados.fin_empresa[i][1]);
                }

                Log.i("", "FIN Valor " + yData[z] + " Data -  " + xData[z] + " " + z);
                z++;
            }
        }

        tab_fin = (LinearLayout) findViewById(R.id.tab_fin);

        /*********************   Barras *****************************************************/
//        b_Chart = new BarChart(this);
//
//        // Adicionar grafico ao tabulador
//        tab_fin.addView(b_Chart);
//        tab_fin.setBackgroundColor(Color.LTGRAY);
//
//        // Configurar grafico
//        b_Chart.setDescription(" Historico Financeiro");
//
//        b_Chart.highlightValues(null);
//
//        // Activar tudo e configurar
//        b_Chart.setDrawBarShadow(true);
//
//        // Activar rotação
//        b_Chart.setRotation(0);
//
//        b_Chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry entry, int i, Highlight highlight) {
//                // mostra valor selecionado
//                if (entry == null)
//                    return;
//                Toast.makeText(EmpDetalActivity.this, xData[entry.getXIndex()] + " = " + entry.getVal() + "€", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//
//        // Configuração de legendas
//        Legend l = b_Chart.getLegend();
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        l.setYEntrySpace(7);
//        l.setXEntrySpace(5);
//
//        // Criar dados para grafico
//        BarDataSet dataSet = new BarDataSet(yVals, "Valores");
////        dataSet.setBarSpacePercent((float) 10.00);
//
//        // aDICIONAR VARIAS CORES
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//        dataSet.setColors(colors);
//
//        // instanciar o grfico
//        BarData bdata = new BarData(xVals, dataSet);
////        bdata.setValueFormatter(new PercentFormatter());
//        bdata.setValueTextSize(8f);
//        bdata.setValueTextColor(Color.GRAY);
//
//        b_Chart.setData(bdata);
//
//        // actualiza grafico
//        b_Chart.invalidate();

        /*********************   Linhas *****************************************************/

        l_Chart = new LineChart(this);

        // Adicionar grafico ao tabulador
        tab_fin.addView(l_Chart);
        tab_fin.setBackgroundColor(Color.LTGRAY);

        // no description text
        l_Chart.setDescription("");
        l_Chart.setNoDataTextDescription("You need to provide data for the chart.");

        // mChart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        l_Chart.setDrawGridBackground(true);
//        chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        // enable touch gestures
        l_Chart.setTouchEnabled(true);

        // enable scaling and dragging
//        l_Chart.setDragEnabled(true);
        l_Chart.setScaleEnabled(true);
        l_Chart.setScaleX(.90f);

        // if disabled, scaling can be done on x- and y-axis separately
        l_Chart.setPinchZoom(true);


        // set custom chart offsets (automatic offset calculation is hereby disabled)
//        l_Chart.setViewPortOffsets((float) 0.1, 0, (float) 0.1, 0);
        YAxis yAxis = l_Chart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        float lx;

        lx = lmax - lmin;
        lx = lx * .10f;

        yAxis.setAxisMaxValue(lmax + lx);
        yAxis.setAxisMinValue(lmin - lx);
        yAxis.setStartAtZero(false);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setAxisLineWidth(2f);
        yAxis.enableGridDashedLine(0f, 0f, 0f);

        XAxis xAxis = l_Chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8f);
        xAxis.setTextColor(Color.RED);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisLineWidth(2f);
        xAxis.setLabelsToSkip(0);

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals_l, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(2f);
        set1.setCircleSize(4f);
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // add data
        l_Chart.setData(data);

        l_Chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                // mostra valor selecionado
                if (entry == null)
                    return;
                Toast.makeText(EmpDetalActivity.this, xData[entry.getXIndex()] + " = " + entry.getVal() + "0€", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // get the legend (only possible after setting data)
        Legend l = l_Chart.getLegend();
        l.setEnabled(false);

        l_Chart.getAxisLeft().setEnabled(true);
        l_Chart.getAxisRight().setEnabled(false);

        l_Chart.getXAxis().setEnabled(true);


        l_Chart.invalidate();
//        l_Chart.animateX(2500);

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
