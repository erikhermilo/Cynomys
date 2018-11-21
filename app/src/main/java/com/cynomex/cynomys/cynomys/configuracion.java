package com.cynomex.cynomys.cynomys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import ServiciosWeb.WebService;

public class configuracion extends AppCompatActivity {


    TextView actionLabel;
    int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        actionLabel = (TextView) findViewById(R.id.txtMensaje);
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
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebService wb = new WebService();
                                wb.RegistrarUsuario2(
                                        "b",
                                        "b",
                                        "2018-12-12",
                                        2,
                                        "b",
                                        "b@mail.com"
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


}
