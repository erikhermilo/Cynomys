package com.cynomex.cynomys.cynomys;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import ServiciosWeb.WebService;

public class ContactoRegistro extends AppCompatActivity {

    Intent intent;
    SoapPrimitive resultString;
    boolean resultObj;
    EditText  EDnomnbre ,EDcorreo, EDpass, EDtelefono;
    int idUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Intent intentLogin = getIntent();
        idUsuario = intentLogin.getIntExtra("idUsuario",0);

        if (idUsuario == 0){
            Intent intent = new Intent(this,login.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactoregistro);

        EDnomnbre = (EditText) findViewById(R.id.txtNombre);
        EDcorreo = (EditText) findViewById(R.id.txtEmail);
        EDtelefono = (EditText) findViewById(R.id.txtTelefono);
    }

    public void registrar(View view){

        intent = new Intent(this,MapsActivity.class);
        intent.putExtra("idUsuario", idUsuario);
        Segundoplano tarea = new Segundoplano();
        tarea.execute();

    }

    private class Segundoplano extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... voids) {

            WebService wb = new WebService();
            resultObj = wb.registrarContactoE(
                        idUsuario,
                        EDnomnbre.getText().toString(),
                        EDtelefono.getText().toString(),
                        EDcorreo.getText().toString());

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if(resultObj) {

                Toast.makeText(ContactoRegistro.this, "Contacto Registrado " , Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
            else
                Toast.makeText(ContactoRegistro.this, "Datos incorrectos", Toast.LENGTH_LONG).show();

        }
    }



}
