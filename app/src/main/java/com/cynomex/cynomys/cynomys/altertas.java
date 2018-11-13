package com.cynomex.cynomys.cynomys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Modelo.Alerta;

public class altertas extends AppCompatActivity {

    int tipoAlerta;
    int idUsuario;
    SoapPrimitive ejecucionSP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altertas);
        tipoAlerta = 0;

        Intent intentMaps= getIntent();
        idUsuario = intentMaps.getIntExtra("idUsuario",0);

        Intent intent = new Intent(this,MapsActivity.class);
    }

    private class Segundoplano extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if (tipoAlerta != 0)
                registrarAlerta(tipoAlerta);
            else
                Toast.makeText(altertas.this, "Ups parece que ha habido un error :/", Toast.LENGTH_LONG).show();

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if(ejecucionSP != null) {
                Toast.makeText(altertas.this, "Alerta registrada " , Toast.LENGTH_LONG).show();

            }
            else
                Toast.makeText(altertas.this, "Ups :/", Toast.LENGTH_LONG).show();

        }
    }

    private void registrarAlerta(int tipoAlerta) {
        String SOAP_ACTION = "http://tempuri.org/registrarMarca";
        String METHOD_NAME = "registrarMarca";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://192.168.137.1:26314/WebService1.asmx";
        Intent intent = getIntent();
        String lon= intent.getStringExtra("lon");
        String lat= intent.getStringExtra("lat");

        try{

            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("longitud",lon);
            request.addProperty("latitud",lat);
            request.addProperty("idtipoalerta",tipoAlerta);
            request.addProperty("idUsuario",idUsuario);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);

            ejecucionSP = (SoapPrimitive) soapEnvelope.getResponse();

        }
        catch (Exception _e){
            Toast.makeText(altertas.this, "Ups parece que ha habido un error :/", Toast.LENGTH_LONG).show();

        }

    }

    public void onClick_btnRobo(View view) {
        tipoAlerta=1;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }
    public void onClick_btnAsalto(View view) {
        tipoAlerta=2;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }
    public void onClick_btndisturbio(View view) {
        tipoAlerta=3;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }

    public void onClick_btnincendio(View view) {
        tipoAlerta=4;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }
    public void onClick_btnviolencia(View view) {
        tipoAlerta=5;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }

    public void onClick_btnsecuestro(View view) {
        tipoAlerta=6;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }
    public void onClick_btnacoso(View view) {
        tipoAlerta=7;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }
    public void onClick_btnherido(View view) {
        tipoAlerta=8;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }
    public void onClick_btnotro(View view) {
        tipoAlerta=9;
        Segundoplano _segundoPlano= new Segundoplano();
        _segundoPlano.execute();
    }




}
