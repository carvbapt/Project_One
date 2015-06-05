package com.android.exemplo.projectone.comercial;

/**
 * Created by Sauca on 05-06-2015.
 */
public class ComDataProvider {

    private int emp_logo;
    private String emp_nome;
    private String emp_data;

    public ComDataProvider(int emp_logo, String emp_nome, String emp_data) {
        this.setEmp_logo(emp_logo);
        this.setEmp_nome(emp_nome);
        this.setEmp_data(emp_data);
    }

    public int getEmp_logo() {
        return emp_logo;
    }

    public void setEmp_logo(int emp_logo) {
        this.emp_logo = emp_logo;
    }

    public String getEmp_nome() {
        return emp_nome;
    }

    public void setEmp_nome(String emp_nome) {
        this.emp_nome = emp_nome;
    }

    public String getEmp_data() {
        return emp_data;
    }

    public void setEmp_data(String emp_data) {
        this.emp_data = emp_data;
    }

}
