package com.cynomex.cynomys.cynomys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class login extends AppCompatActivity {
    private EditText EDcorreo, EDPass;
    SoapPrimitive resultString;
    SoapObject resultObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EDcorreo= (EditText) findViewById(R.id.editText);
        EDPass= (EditText) findViewById(R.id.editText2);

    }
    public void loguear(View view){

        Intent intent = new Intent(this,MapsActivity.class);

        login.Segundoplano tarea = new login.Segundoplano();
        tarea.execute();

        if(resultObj != null) {
            Toast.makeText(login.this, "Rdgsgzfghsfhs " , Toast.LENGTH_LONG).show();

            startActivity(intent);

        }

    }



    private class Segundoplano extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... voids) {
            logeaff();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

        }
    }
    public void Registro(View view){

        Intent intent = new Intent(this,registrous.class);

        startActivity(intent);
    }


    public boolean logeaff() {

        String SOAP_ACTION = "http://tempuri.org/LoginUser";
        String METHOD_NAME = "LoginUser";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://192.168.137.1:26314/WebService1.asmx";

        try{

            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("Email", EDcorreo.getText().toString());
            request.addProperty("Contraseña", EDPass.getText().toString());



            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);

            //resultString = (SoapPrimitive) soapEnvelope.getResponse();

            resultObj= (SoapObject) soapEnvelope.getResponse();


            if (resultObj!=null)
                Toast.makeText(login.this,"Usuario inseertado " , Toast.LENGTH_LONG).show();




            return true;

        }catch (Exception ex){
            Toast.makeText(login.this,"ERROR: " + ex.getMessage(), Toast.LENGTH_LONG).show();

            return false;

        }
    }

}
