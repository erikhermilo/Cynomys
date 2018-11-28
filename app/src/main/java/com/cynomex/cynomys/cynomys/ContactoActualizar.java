package com.cynomex.cynomys.cynomys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.serialization.SoapPrimitive;

import Modelo.ContactoEmergencia;
import ServiciosWeb.WebService;

public class ContactoActualizar extends AppCompatActivity {

    Intent intent;
    SoapPrimitive resultString;
    boolean resultObj;
    EditText EDnomnbre ,EDcorreo, EDpass, EDtelefono;
    int idUsuario;
    ContactoEmergencia contactoEmergencia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        contactoEmergencia = new ContactoEmergencia();

        intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario",0);

        contactoEmergencia.setIdContactoEmergencia(intent.getIntExtra("getIdContactoEmergencia",0));
        contactoEmergencia.setNombre(intent.getStringExtra("getNombre"));
        contactoEmergencia.setEmail(intent.getStringExtra("getEmail"));
        contactoEmergencia.setTelefono(intent.getStringExtra("getTelefono"));
        contactoEmergencia.setIdUsuario(intent.getIntExtra("getIdUsuario",0));

        if (idUsuario == 0){
            Intent intent = new Intent(this,login.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactoactualizar);

        EDnomnbre = (EditText) findViewById(R.id.txtNombre);
        EDcorreo = (EditText) findViewById(R.id.txtEmail);
        EDtelefono = (EditText) findViewById(R.id.txtTelefono);

        EDnomnbre.setText(contactoEmergencia.getNombre());
        EDcorreo.setText(contactoEmergencia.getEmail());
        EDtelefono.setText(contactoEmergencia.getTelefono());

    }

    public void cancelar(View view){

        this.finish();

    }

    public void actualizarContacto(View view){

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
            resultObj = wb.UpdateContacto(
                    EDnomnbre.getText().toString(),
                    EDtelefono.getText().toString(),
                    EDcorreo.getText().toString(),
                    contactoEmergencia.getIdContactoEmergencia());

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if(resultObj) {

                Toast.makeText(ContactoActualizar.this, "Contacto Actualizado " , Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
            else
                Toast.makeText(ContactoActualizar.this, "Datos incorrectos", Toast.LENGTH_LONG).show();

        }
    }


}
