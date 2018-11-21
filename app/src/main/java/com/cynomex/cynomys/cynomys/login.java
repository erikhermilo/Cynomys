package com.cynomex.cynomys.cynomys;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class login extends AppCompatActivity {

    private Context thisContext=this;
    private EditText EDcorreo, EDPass;
    SoapPrimitive resultString;
    SoapObject resultObj;
    Intent intent;
    Button mostrarNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!isMyServiceRunning(ServiceApp.class)){ //método que determina si el servicio ya está corriendo o no
            startService(new Intent(thisContext, ServiceApp.class));
            Log.d("App", "Service started");
        } else {
            Log.d("App", "Service already running");
        }




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
                NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                int icono = R.mipmap.ic_launcher;
                Intent i=new Intent(login.this, login.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(login.this, 0, i, 0);

                mBuilder =new NotificationCompat.Builder(getApplicationContext())
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(icono)
                        .setContentTitle("Titulo")
                        .setContentText("Hola que tal?")
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setAutoCancel(true);


                mNotifyMgr.notify(1, mBuilder.build());




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
