package com.android.exemplo.projectone.empresa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.exemplo.projectone.AgendaActivity;
import com.android.exemplo.projectone.EmpresaActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.comercial.ComDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sauca on 06-06-2015.
 */
public class EmpAdapter extends ArrayAdapter {

    public final static String EXTRA_MESSAGE = "com.android.examplo.projectone.MESSAGE";
    List list = new ArrayList();
    View row;
    public EmpAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        final DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_emprow, parent, false);
            handler = new DataHandler();
            handler.com_mail = (ImageButton) row.findViewById(R.id.BT_comail);
            handler.com_nome = (TextView) row.findViewById(R.id.com_nome);
            handler.com_data = (TextView) row.findViewById(R.id.com_data);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }

        EmpDataProvider dataProvider;
        dataProvider = (EmpDataProvider) this.getItem(position);
        handler.com_nome.setText(dataProvider.getCom_nome());
        handler.com_data.setText(dataProvider.getCom_data());

        handler.com_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "TESTE EMAIL \n Nome - " + handler.com_nome.getText(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(EXTRA_MESSAGE, handler.com_nome.getText());
                v.getContext().startActivity(intent);
            }
        });

        return row;
    }

    static class DataHandler {
        ImageButton com_mail;
        TextView com_nome;
        TextView com_data;
    }

}
