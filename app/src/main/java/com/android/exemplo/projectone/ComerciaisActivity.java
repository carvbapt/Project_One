package com.android.exemplo.projectone;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.exemplo.projectone.comercial.ComDetalActivity;
import com.android.exemplo.projectone.helper.Base_Activity;
import com.android.exemplo.projectone.helper.Dados;


public class ComerciaisActivity extends Base_Activity {

    public final static String EXTRA_MESSAGE = "com.android.examplo.projectone.MESSAGE2";

    ListView list;
    ArrayAdapter<String> adapter;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comerciais);

        list = (ListView) findViewById(R.id.lv_com);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Dados.Comerciais);
        list.setAdapter(adapter);

        // Seleciona linha
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(ComerciaisActivity.this, ComDetalActivity.class);

                intent.putExtra(EXTRA_MESSAGE, Dados.Comerciais[position]);
//                Toast.makeText(ComerciaisActivity.this, Dados.Comerciais[position], Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }


}
