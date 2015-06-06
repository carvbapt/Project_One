package com.android.exemplo.projectone.empresa;

import android.widget.ImageButton;

/**
 * Created by Sauca on 06-06-2015.
 */
public class EmpDataProvider {

    private ImageButton com_mail;
    private String com_nome;
    private String com_data;

    public EmpDataProvider(ImageButton com_mail, String com_nome, String com_data) {
        this.com_mail = com_mail;
        this.com_nome = com_nome;
        this.com_data = com_data;
    }

    public EmpDataProvider(String com_nome, String com_data) {
        this.com_nome = com_nome;
        this.com_data = com_data;
    }

    public ImageButton getCom_mail() {
        return com_mail;
    }

    public void setCom_mail(ImageButton com_mail) {
        this.com_mail = com_mail;
    }

    public String getCom_nome() {
        return com_nome;
    }

    public void setCom_nome(String com_nome) {
        this.com_nome = com_nome;
    }

    public String getCom_data() {
        return com_data;
    }

    public void setCom_data(String com_data) {
        this.com_data = com_data;
    }

}
