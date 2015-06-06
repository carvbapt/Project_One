package com.android.exemplo.projectone.comercial;

import android.content.Intent;
import android.content.res.TypedArray;

import android.os.Bundle;
import android.util.Log;
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
    String ch, aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comdetal);


        // Get the message from the intent
        Intent intent = getIntent();
        message = intent.getStringExtra(ComerciaisActivity.EXTRA_MESSAGE);
//        Toast.makeText(ComDetalActivity.this, message, Toast.LENGTH_SHORT).show();
        // Create the text view
        TextView textView = (TextView) findViewById(R.id.txt_detcom);
        textView.setText(message);

        list = (ListView) findViewById(R.id.LVcomdetal);

        int i, ind = -1, z = 0;

        // Carregar imagem na lista
//        String[] logo2 = {"@drawable/" + R.drawable.comercial,
//                "@drawable/" + R.drawable.agenda,
//                "@drawable/" + R.drawable.empresa,
//                "@drawable/" + R.drawable.comercial};

//        emp_logo = getResources().obtainTypedArray(Integer.parseInt(String.valueOf(logo2)));
        emp_logo = getResources().obtainTypedArray(R.array.logo);

        // Carregar dados na lista
        for (i = 0; i < Dados.Comerciais.length; i++) {
            if (message.equals(Dados.Comerciais[i])) {
                ind = i;
                break;
            }
        }

//        Toast.makeText(ComDetalActivity.this, "" + ind, Toast.LENGTH_SHORT).show();
        int count = 0;
        for (i = 0; i < Dados.com_empresa.length; i++) {
            if (Dados.com_empresa[i][1].equals("" + ind)) {
                count++;
            }
        }
        emp_nome = new String[count];
        emp_data = new String[count];

        for (i = 0; i < Dados.com_empresa.length; i++) {
            if (Dados.com_empresa[i][1].equals("" + ind)) {
                aux = Dados.Empresas[Integer.parseInt(Dados.com_empresa[i][0])];

                emp_nome[z] = aux.substring(0, (aux.length() >= 21) ? 20 : aux.length()) + "...";
                str_dt = new StringBuilder(Dados.com_empresa[i][2]);
//                ch = "-";
//                str_dt.insert(2, ch);
//                str_dt.insert(5, ch);
                emp_data[z] = "" + String.valueOf(str_dt);
                Log.i("", "DAD-" + emp_nome[z] + " - " + emp_data[z] + " " + emp_nome.length);
                z++;

            }
        }
        adapter = new ComAdapter(getApplicationContext(), R.layout.activity_comrow);
        list.setAdapter(adapter);
        if (!emp_nome.equals("")) {
            for (i = 0; i < emp_nome.length; i++) {
                ComDataProvider dataProvider = new ComDataProvider(emp_logo.getResourceId(i, -1), emp_nome[i],
                        emp_data[i]);
                adapter.add(dataProvider);
            }
        } else
            Toast.makeText(ComDetalActivity.this, "Comercial não tem Empresas atribuidas", Toast.LENGTH_SHORT).show();

    }
}
