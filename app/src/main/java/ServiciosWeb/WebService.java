package ServiciosWeb;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {
    SoapPrimitive resultString;

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
