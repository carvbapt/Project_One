package com.android.exemplo.projectone.empresa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.comercial.ComDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sauca on 06-06-2015.
 */
public class EmpAdapter extends ArrayAdapter {

    List list = new ArrayList();

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
        View row;
        row = convertView;
        DataHandler handler;
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

//        row.setOnClickListener((View.OnClickListener) this);

        EmpDataProvider dataProvider;
        dataProvider = (EmpDataProvider) this.getItem(position);
        handler.com_nome.setText(dataProvider.getCom_nome());
        handler.com_data.setText(dataProvider.getCom_data());

        return row;
    }

    static class DataHandler {
        ImageButton com_mail;
        TextView com_nome;
        TextView com_data;
    }

}
