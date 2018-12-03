package ServiciosWeb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import java.sql.Date;


import Modelo.ContactoEmergencia;
import Modelo.Usuario;

public class WebService {
    SoapPrimitive ejecucionSP;
SoapObject resultObj;
    String SOAP_ACTION, METHOD_NAME , NAMESPACE , URL ;

    Modelo.Usuario _usu;

    public WebService(){
         ejecucionSP = null;

         NAMESPACE = "http://tempuri.org/";
         URL = "http://192.168.137.1:26314/WebService1.asmx";
         SOAP_ACTION = NAMESPACE;

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


    public boolean RegistrarUsuario(String nick, String nombre, String correo, String Fechan, String Contrasena, int Sexo) {
        METHOD_NAME = "setUsuario";
        SOAP_ACTION += METHOD_NAME;

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
            ejecucionSP = (SoapPrimitive) soapEnvelope.getResponse();

            return true;
        }catch (Exception ex){
            return false;
        }
    }


    public boolean registrarContactoE (int _idUsuario, String _nombre, String _telefono, String _email) {

        METHOD_NAME = "registrarContacto";
        SOAP_ACTION += METHOD_NAME;

        try{
            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nombre", _nombre);
            request.addProperty("telefono", _telefono);
            request.addProperty("email", _email);
            request.addProperty("idUsuario", _idUsuario);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);

            ejecucionSP = (SoapPrimitive) soapEnvelope.getResponse();

            String phone = _telefono;
            SmsManager sms = SmsManager.getDefault();
            String text = "Has sido registrado como contacto de emergencia del usuario: " + _nombre + ". Cynomys";
            sms.sendTextMessage(phone, null, text , null, null);


            return true;

        }
        catch (Exception _e){
            System.out.println("Ups parece que ha habido un error :/");
            return false;
        }
    }

    public ContactoEmergencia GetContacto (int idUsuario) {

        METHOD_NAME = "GetContacto";
        SOAP_ACTION += METHOD_NAME;

        try{
            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idUsuario", idUsuario);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);

            resultObj= (SoapObject) soapEnvelope.getResponse();

            ContactoEmergencia contactoEmergencia = new ContactoEmergencia();
            contactoEmergencia.setIdContactoEmergencia(Integer.parseInt(resultObj.getProperty("IdContactoEmergencia").toString()));
            contactoEmergencia.setNombre(resultObj.getProperty("Nombre").toString());
            contactoEmergencia.setEmail(resultObj.getProperty("Telefono").toString());
            contactoEmergencia.setTelefono(resultObj.getProperty("Email").toString());
            contactoEmergencia.setIdUsuario(Integer.parseInt(resultObj.getProperty("IdUsuario").toString()));


            return contactoEmergencia;

        }
        catch (Exception _e){
            System.out.println("Ups parece que ha habido un error :/");
            return null;
        }
    }

    public Boolean UpdateContacto(String _nombre, String _telefono, String _email, int _idContactoemergencia)
    {
        METHOD_NAME = "UpdateContacto";
        SOAP_ACTION += METHOD_NAME;

        try{
            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nombre", _nombre);
            request.addProperty("telefono", _telefono);
            request.addProperty("email", _email);
            request.addProperty("idContactoemergencia", _idContactoemergencia);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);

            ejecucionSP = (SoapPrimitive) soapEnvelope.getResponse();

            
            return true;

        }
        catch (Exception _e){
            System.out.println("Ups parece que ha habido un error :/");
            return false;
        }
    }

    public Boolean registrarDenuncia(int _idAlerta, String _texto, int _idUsuario)
    {
        METHOD_NAME = "setDenuncia";
        SOAP_ACTION += METHOD_NAME;

        try{
            SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idAlerta", _idAlerta);
            request.addProperty("strMensaje", _texto);
            request.addProperty("idUsuario", _idUsuario);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);

            ejecucionSP = (SoapPrimitive) soapEnvelope.getResponse();

            return true;

        }
        catch (Exception _e){
            System.out.println("Ups parece que ha habido un error :/");
            return false;
        }
    }

}
