package com.android.exemplo.projectone.comercial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.exemplo.projectone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sauca on 05-06-2015.
 */
public class ComAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ComAdapter(Context context, int resource) {
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
            row = inflater.inflate(R.layout.activity_comrow, parent, false);
            handler = new DataHandler();
            handler.emp_logo = (ImageView) row.findViewById(R.id.emp_logo);
            handler.emp_nome = (TextView) row.findViewById(R.id.emp_nome);
            handler.emp_data = (TextView) row.findViewById(R.id.emp_data);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }

        ComDataProvider dataProvider;
        dataProvider = (ComDataProvider) this.getItem(position);





//        handler.emp_logo.setImageResource(dataProvider.getEmp_logo()); comentei as 19.42
        handler.emp_nome.setText(dataProvider.getEmp_nome());
        handler.emp_data.setText(dataProvider.getEmp_data());

        boolean emptyImage = true;
        if (dataProvider.getEmp_nome().contains("Rolear")){
//            Picasso.with(getContext()).setIndicatorsEnabled(true);    debugger
            Picasso.with(getContext()).load("http://www.rolearon.pt/images/logo_rolearon.png").into(handler.emp_logo);
            emptyImage=false;
        }
        if (dataProvider.getEmp_nome().contains("Algardata")){
//            Picasso.with(getContext()).setIndicatorsEnabled(true);    debugger
            Picasso.with(getContext()).load("http://www.algardata.com/images/algardata/logo.png").into(handler.emp_logo);
            emptyImage=false;
        }
        if (dataProvider.getEmp_nome().contains("guas do Algarve")){
//            Picasso.with(getContext()).setIndicatorsEnabled(true);    debugger
            Picasso.with(getContext()).load("http://www.adp.pt/files/1.jpg").into(handler.emp_logo);
            emptyImage=false;
        }
        if (dataProvider.getEmp_nome().contains("ACL -")){
//            Picasso.with(getContext()).setIndicatorsEnabled(true);    debugger
            Picasso.with(getContext()).load("http://www.axo.pt/resources/ACL_other1_500_500.jpg").into(handler.emp_logo);
            emptyImage=false;
        }
        if (dataProvider.getEmp_nome().contains("TU, Uni")){
//            Picasso.with(getContext()).setIndicatorsEnabled(true);    debugger
            Picasso.with(getContext()).load("http://s3.portugalio.com/u/tu/rq/turquesoriginal-unipessoal-lda-1395509040_big.png").into(handler.emp_logo);
            emptyImage=false;
        }
        if (emptyImage) {
            Picasso.with(getContext()).load("http://trilhos.visitazores.com/sites/all/modules/azores_trails/images/weather/not_available.png").into(handler.emp_logo);
        }

        System.out.println("getview:" + position + " " + convertView);

        return row;
    }

    static class DataHandler {
        ImageView emp_logo;
        TextView emp_nome;
        TextView emp_data;
    }
}
