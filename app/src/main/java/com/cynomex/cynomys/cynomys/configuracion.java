package com.cynomex.cynomys.cynomys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Modelo.ContactoEmergencia;
import ServiciosWeb.WebService;

import android.telephony.SmsManager;

public class configuracion extends AppCompatActivity {

    Context context= this;
    TextView actionLabel;
    int idUsuario;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        actionLabel = (TextView) findViewById(R.id.txtMensaje);
    }

    public void GuiasVistas(View view){

        intent = new Intent(this,guias.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
    }


    public void Alertas(View view){

        intent = new Intent(this,altertas.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
    }

    public void Denuncia(View view){

        intent = new Intent(this, denuncia.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
    }

    private class SegundoplanoContacto extends AsyncTask<Void,Void,Void> {
        WebService wb = new WebService();
        ContactoEmergencia contactoEmergencia;

        @Override
        protected void onPreExecute() {
            checkSMSStatePermission();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            contactoEmergencia = wb.GetContacto(idUsuario);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if( contactoEmergencia == null ) {

                intent = new Intent(context, ContactoRegistro.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
            else {
                intent = new Intent(context, ContactoActualizar.class);
                intent.putExtra("idUsuario", idUsuario);

                intent.putExtra("getIdContactoEmergencia",contactoEmergencia.getIdContactoEmergencia() );
                intent.putExtra("getNombre",contactoEmergencia.getNombre() );
                intent.putExtra("getEmail",contactoEmergencia.getEmail() );
                intent.putExtra("getTelefono", contactoEmergencia.getTelefono() );
                intent.putExtra("getIdUsuario", contactoEmergencia.getIdUsuario() );

                startActivity(intent);
            }

        }
    }


    public void Contacto(View view){

        SegundoplanoContacto segundoplanoContacto = new SegundoplanoContacto();
        segundoplanoContacto.execute();

    }


    @Override
        // catches the onKeyDown button event
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                actionLabel.setText("VOLUME_UP key pressed");
                //insertar();
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                actionLabel.setText("VOLUME_DOWN key pressed");
                insertar();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
        // catches the onKeyUp button event
       @Override
       public boolean onKeyUp(int keyCode, KeyEvent event) {
           switch (keyCode) {
               case KeyEvent.KEYCODE_BACK:
                   actionLabel.setText("KEYCODE_BACK key released");
                   return true;
               case KeyEvent.KEYCODE_VOLUME_UP:
                   actionLabel.setText("VOLUME_UP key released");
                   return true;
               case KeyEvent.KEYCODE_VOLUME_DOWN:
                   actionLabel.setText("VOLUME_DOWN key released");
                   return true;
           }
           return super.onKeyDown(keyCode, event);
       }


           // works for API level 5 and lower
       @Override
       public void onBackPressed() {
           actionLabel.setText("BACK key pressed");
           super.onBackPressed();
       }

           // catches the long press button event (longer than 2 seconds)

       @Override
       public boolean onKeyLongPress(int keyCode, KeyEvent event) {
           Toast.makeText(this, "Pressed for a long time", Toast.LENGTH_SHORT).show();
           return true;
       }


           // catches the on touch event on screen and shows the specific pixels
           // touched
       @Override
       public boolean onTouchEvent(MotionEvent event) {
           float x = event.getX();
           float y = event.getY();
           actionLabel.setText("Touch press on x: " + x + " y: " + y);
           return true;
       }
       */
    public void insertar(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebService wb = new WebService();
                                wb.RegistrarUsuario(
                                        "b",
                                        "b",
                                        "b@mail.com",
                                        "2018-12-12",
                                        "b",
                                        2
                                );
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();

    }


    public void enviarSMS(View view){
        if ( checkSMSStatePermission() ){
            String phone = "5523730504";
            SmsManager sms = SmsManager.getDefault();
            String text = "TE INFINITO HERMI BONITO <3";
            sms.sendTextMessage(phone, null, text , null, null);
        }
    }

    private boolean checkSMSStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para enviar SMS.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 225);
            return false;
        } else {
            Log.i("Mensaje", "Se tiene permiso para enviar SMS!");
            return true;
        }
    }


}
