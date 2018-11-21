package ServiciosWeb;

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

import java.sql.Date;


import Modelo.Usuario;

public class WebService {
    SoapPrimitive ejecucionSP;
    String SOAP_ACTION, METHOD_NAME , NAMESPACE , URL ;

    Modelo.Usuario _usu;

    public WebService(){
         ejecucionSP = null;

         NAMESPACE = "http://tempuri.org/";
         URL = "http://192.168.137.1:26314/WebService1.asmx";
         SOAP_ACTION = NAMESPACE;

    }


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


    public SoapPrimitive registrarAlerta(int idUsuario,int tipoAlerta, String lon, String lat) {

        METHOD_NAME = "registrarMarca";
        SOAP_ACTION += METHOD_NAME;

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
            return ejecucionSP;

        }
        catch (Exception _e){
            System.out.println("Ups parece que ha habido un error :/");
            return null;
        }

    }

    public void RegistrarUsuario2(String _nickname, String _password, String _fechaN, int _idSexo, String _nombre, String _email){
        _usu = new Usuario();
        _usu.setUsuario1(_nickname);
        _usu.setPassword(_password);
        _usu.setFecha_nacimiento(Date.valueOf(_fechaN));
        _usu.setIdsexo(_idSexo);
        _usu.setNombre(_nombre);
        _usu.setEmail(_email);

        Segundoplano tarea = new Segundoplano();
        tarea.execute();
    }
    public boolean RegistrarUsuario() {

        METHOD_NAME = "setUsuario";
        SOAP_ACTION += METHOD_NAME;
        try{

            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("nickname",_usu.getUsuario1());
            request.addProperty("password",_usu.getPassword());
            request.addProperty("fechaN",_usu.getFecha_nacimiento().toString());

            request.addProperty("sexo", _usu.getIdsexo());
            request.addProperty("nombre", _usu.getNombre());
            request.addProperty("email", _usu.getEmail());

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            ejecucionSP = (SoapPrimitive) soapEnvelope.getResponse();

            return true;
        }catch (Exception ex){

            return false;
        }
    }


/*
    private class Segundoplano extends AsyncTask <Void,Void,Void> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
        }
    }

    public boolean RegistrarUsuario(String nick, String nombre, String correo, String Fechan, String Contrasena, int Sexo) {
        String SOAP_ACTION = "http://tempuri.org/setUsuario";
        String METHOD_NAME = "setUsuario";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "http://192.168.137.1:26314/WebService1.asmx";

        try{

            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("nickname", nick);
            request.addProperty("password", Contrasena);
            request.addProperty("fechaN", Fechan);
            request.addProperty("sexo", Sexo);
            request.addProperty("nombre", nombre);
            request.addProperty("email", correo);

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
*/
}
