package com.android.exemplo.projectone.comercial;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.exemplo.projectone.ComerciaisActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Base_Activity;
import com.android.exemplo.projectone.helper.Dados;

public class ComDetalActivity extends Base_Activity {

    String message;

    ListView list;
    TypedArray emp_logo;
    String[] emp_nome;
    String[] emp_data;
    ComAdapter adapter;
    StringBuilder str_dt;
    String ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comdetal);


        // Get the message from the intent
        Intent intent = getIntent();
        message = intent.getStringExtra(ComerciaisActivity.EXTRA_MESSAGE2);
        Toast.makeText(ComDetalActivity.this, message, Toast.LENGTH_SHORT).show();
        // Create the text view
        TextView textView = (TextView) findViewById(R.id.Tv1);
        textView.setText(message);

        list = (ListView) findViewById(R.id.LVcomdetal);

        int i, ind = -1, z = 0;

//        String[] logo={"@drawable/comercial","@drawable/agenda","@drawable/empresa"};
//        emp_logo = getResources().obtainTypedArray(Integer.parseInt(logo));

        for (i = 0; i < Dados.Comerciais.length; i++) {
            if (message.equals(Dados.Comerciais[i])) {
                ind = i;
                break;
            }
        }

        Toast.makeText(ComDetalActivity.this, "" + ind, Toast.LENGTH_SHORT).show();

        for (i = 0; i < Dados.com_empresa.length; i++)
            if (Dados.com_empresa[i][1] == "" + ind) {
                Log.i("", "DAD-" + Dados.Empresas[Integer.parseInt(Dados.com_empresa[i][0])]);
                emp_nome[z] = Dados.Empresas[Integer.parseInt(Dados.com_empresa[i][0])];
                str_dt = new StringBuilder(Dados.com_empresa[i][2]);
                ch = "-";
                str_dt.insert(2, ch);
                str_dt.insert(5, ch);
                emp_data[z] = String.valueOf(str_dt);
                z++;

                Log.i("", "DAD2-" + emp_nome[z] + " " + emp_data[z]);
            }

        adapter = new ComAdapter(getApplicationContext(), R.layout.activity_comrow);
        list.setAdapter(adapter);
        for (i = 0; i < emp_nome.length; i++) {
            ComDataProvider dataProvider = new ComDataProvider(emp_logo.getResourceId(i, -1), emp_nome[i],
                    emp_data[i]);
            adapter.add(dataProvider);
        }

    }
}
