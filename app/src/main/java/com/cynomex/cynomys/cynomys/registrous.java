package com.cynomex.cynomys.cynomys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class registrous extends AppCompatActivity {
private EditText EDnickna, EDcorreo, EDnombre, EDFechan, EDPassport;
private RadioButton RADmasculino, RADfemenino;
private String Strnick, Strcorreo, Strnombre, Strfechan, Strpassport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrous);

        EDnickna = (EditText) findViewById(R.id.txtnick);
        EDcorreo = (EditText) findViewById(R.id.txtemail);
        EDnombre = (EditText) findViewById(R.id.txtnombre);
        EDPassport = (EditText) findViewById(R.id.txtpassword);
        EDFechan = (EditText) findViewById(R.id.datefechan);

        RADmasculino = (RadioButton) findViewById(R.id.radioButton);
        RADfemenino = (RadioButton) findViewById(R.id.radioButton2);

    }
    public void Registro(View view){
        Segundoplano tarea = new Segundoplano();
        tarea.execute();

        Toast.makeText(registrous.this,"Registro: " + resultString.toString(), Toast.LENGTH_LONG).show();

        this.finish();
    }
    SoapPrimitive resultString;


    private class Segundoplano extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... voids) {
            RegistrarUsuario();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
        }
    }

    public boolean RegistrarUsuario() {
        String SOAP_ACTION = "http://tempuri.org/setUsuario";
        String METHOD_NAME = "setUsuario";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://192.168.137.1:26314/WebService1.asmx";

        try{

            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("nickname", EDnickna.getText().toString());
            request.addProperty("password", EDPassport.getText().toString());
            request.addProperty("fechaN", EDFechan.getText().toString());
            if(RADmasculino.equals(true)){
            request.addProperty("sexo", 1);}else{
                request.addProperty("sexo", 2);
            }
            request.addProperty("nombre", EDnombre.getText().toString());
            request.addProperty("email", EDcorreo.getText().toString());

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            return true;
        }catch (Exception ex){

            return false;
        }
    }
}
