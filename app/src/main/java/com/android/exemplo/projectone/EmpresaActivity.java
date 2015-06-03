package com.android.exemplo.projectone;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class EmpresaActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.android.examplo.projectone.MESSAGE";

    ListView list;
    ArrayAdapter<String> adapter;
    String message;
    Intent intent;
    TextView dados;
    String[] str_aux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        dados = (TextView) findViewById(R.id.dados);

        list = (ListView) findViewById(R.id.lv_emp);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Dados.Empresas);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                message = "Pos - " + position + "\n" + "Nome - " + Dados.Empresas[position];

                intent = new Intent(EmpresaActivity.this, EmpDetalActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message);
//                Toast.makeText(EmpresaActivity.this,message,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_empresa, menu);
//        return true;Admi

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_empresa, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EmpresaActivity.this.adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_search:
//                openSearch();
                return true;
            case R.id.action_settings:
                //  openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}