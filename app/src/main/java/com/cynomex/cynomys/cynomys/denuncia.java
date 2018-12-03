package com.cynomex.cynomys.cynomys;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.serialization.SoapPrimitive;

import Modelo.ContactoEmergencia;
import ServiciosWeb.WebService;

public class denuncia extends AppCompatActivity {
    Intent intent;
    SoapPrimitive resultString;
    boolean resultObj;
    EditText EDidalerta ,EDmensaje;
    int idUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario",0);

        if (idUsuario == 0){
            Intent intent = new Intent(this,login.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        EDidalerta = (EditText) findViewById(R.id.txtIDalerta);
        EDmensaje = (EditText) findViewById(R.id.txtTexto);

    }

    public void cancelar(View view){

        this.finish();

    }

    public void registrarDenuncia(View view){

        /*intent = new Intent(this,MapsActivity.class);
        intent.putExtra("idUsuario", idUsuario);
        */
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
            resultObj = wb.registrarDenuncia(
                    Integer.parseInt(EDidalerta.getText().toString()),
                    EDmensaje.getText().toString(),
                    idUsuario);

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if(resultObj) {

                Toast.makeText(denuncia.this, "Denuncia Realizada " , Toast.LENGTH_LONG).show();
                //startActivity(intent);
                finish();

            }
            else
                Toast.makeText(denuncia.this, "Datos incorrectos", Toast.LENGTH_LONG).show();

        }
    }

}
