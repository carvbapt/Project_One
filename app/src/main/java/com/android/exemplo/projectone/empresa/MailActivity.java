package com.android.exemplo.projectone.empresa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.exemplo.projectone.empresa.EmpDetalActivity;
import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Base_Activity;
import com.android.exemplo.projectone.helper.Dados;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailActivity extends Base_Activity implements View.OnClickListener {

    String message;
    int ind;

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText ed_tomail, ed_submail, ed_txtmail;
    String rec, subject, txtmessage;
    Button ed_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        context = this;

        ed_send = (Button) findViewById(R.id.bt_sendmail);
        ed_tomail = (EditText) findViewById(R.id.ed_tomail);
        ed_submail = (EditText) findViewById(R.id.ed_submail);
        ed_txtmail = (EditText) findViewById(R.id.ed_txtmail);

        // Get the message from the intent
        Intent intent = getIntent();
        message = intent.getStringExtra(EmpAdapter.EXTRA_MESSAGE);
        Log.i("", "MSG-" + message);
        ind = 0;
        for (int i = 0; i <= Dados.Comerciais.length; i++) {
            if (Dados.Comerciais[i].equals(message)) {
                ind = i;
                break;
            }
        }

        ed_tomail.setText(Dados.Email[ind]);
        ed_tomail.setEnabled(false);
        ed_tomail.setCursorVisible(false);
//        Toast.makeText(this, "TESTE EMAIL \n Nome - " + Dados.Email[ind], Toast.LENGTH_LONG).show();

        ed_send.setOnClickListener(this);
    }

    public void onClick(View v) {
        rec = ed_tomail.getText().toString();
        subject = ed_submail.getText().toString();
        txtmessage = ed_txtmail.getText().toString();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("carvbapt@gmail.com", "carvalhobaptista");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();

    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

//            try {
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress("carvbapt@gmail.com"));
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("carbapt@gmail.com"));
//                message.setSubject(subject);
//                message.setContent(txtmessage, "text/html; charset=utf-8");
//                Transport.send(message);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


//            FUNCIONA FORA DA APLICAcaO
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{rec});
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT   , txtmessage);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplication(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
//            ed_tomail.setText("");
//            ed_txtmail.setText("");
//            ed_submail.setText("");
//            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
//            Intent intent= new Intent(MailActivity.this,EmpDetalActivity.class);
//            startActivity(intent);
        }
    }
}
