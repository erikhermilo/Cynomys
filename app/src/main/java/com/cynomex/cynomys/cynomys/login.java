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
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EDcorreo= (EditText) findViewById(R.id.editText);
        EDPass= (EditText) findViewById(R.id.editText2);


    }
    public void loguear(View view){

        intent = new Intent(this,MapsActivity.class);

        Segundoplano tareaLogin = new Segundoplano();
        tareaLogin.execute();



    }



    private class Segundoplano extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... voids) {
            LogearUsuario();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if(resultObj != null) {
                Toast.makeText(login.this, "BIENBENIDO "+resultObj.getProperty("Email") , Toast.LENGTH_LONG).show();

                intent.putExtra("idUsuario", Integer.parseInt(resultObj.getProperty("IdUsuario").toString()));
                startActivity(intent);



            }
            else
                Toast.makeText(login.this, "Datos incorrectos", Toast.LENGTH_LONG).show();

        }
    }
    public void Registro(View view){

        Intent intent = new Intent(this,registrous.class);
        startActivity(intent);
    }


    public boolean LogearUsuario() {

        String SOAP_ACTION = "http://tempuri.org/LoginUser";
        String METHOD_NAME = "LoginUser";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://192.168.137.1:26314/WebService1.asmx";

        try{

            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("Email",EDcorreo.getText().toString());
            request.addProperty("Contrasenia", EDPass.getText().toString());

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);


            // SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            // System.out.println("response"+response.toString() + "  "+response.getProperty(0).toString());

            resultObj= (SoapObject) soapEnvelope.getResponse();


            if (resultObj!=null)
            return true;
            else
                return false;

        }catch (Exception ex){
            System.out.println("------------------------------------");
            System.out.println("Error!!!  "+ ex.getMessage());
            return false;

        }
    }

}
