package com.cynomex.cynomys.cynomys;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
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


///////////////


public class login extends AppCompatActivity {

    private Context thisContext=this;
    private EditText EDcorreo, EDPass;
    SoapPrimitive resultString;
    SoapObject resultObj;
    Intent intent;
    Button mostrarNotificacion;

    /////////
    private Button boton;
    NotificationCompat.Builder notificacion;
    private static final int idUnica = 51623;

    /////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        if (!isMyServiceRunning(ServiceApp.class)){ //método que determina si el servicio ya está corriendo o no
            startService(new Intent(thisContext, ServiceApp.class));
            startForegroundService(new Intent(thisContext, ServiceApp.class));

            Log.d("App", "Service started");
        } else {
            Log.d("App", "Service already running");
        }
*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EDcorreo= (EditText) findViewById(R.id.editText);
        EDPass= (EditText) findViewById(R.id.editText2);

    }

    private boolean isMyServiceRunning(Class<ServiceApp> serviceAppClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceAppClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;

    }


    Button notify;
    String tkn;
    public  void loguear2(View view){
        tkn= FirebaseInstanceId.getInstance().getToken();
        new Notify().execute();

    }

    public class Notify extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=AIzaSyDlidqWUpQjgHLvdRy1GOYof_V6nkiDeWc");
                conn.setRequestProperty("Content-Type","application/json");

                JSONObject json = new JSONObject();

                json.put("to", tkn);


                JSONObject info = new JSONObject();
                info.put("title", "TechnoWeb");   // Notification title
                info.put("body", "Hello Test notification"); // Notification body

                json.put("notification", info);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();

            } catch (Exception e) {
                Log.d("Error", "" + e);
            }


            return null;
        }

    }
    ///

    public void loguear(View view){

       // startService(new Intent(thisContext, ServiceApp.class));
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
