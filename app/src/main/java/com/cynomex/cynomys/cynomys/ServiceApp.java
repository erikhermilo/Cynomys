package com.cynomex.cynomys.cynomys;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import ServiciosWeb.WebService;

import static android.support.constraint.Constraints.TAG;


public class ServiceApp extends Service {

    @Override
    public void onCreate() {


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //startService(new Intent(thisContext, ServiceApp.class));
    }



    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){

        /*
        configuracion conf = new configuracion();
        conf.insertar();
        */
        System.out.println("*HERMI* EL SERVICIO ESTA EJECUTADO");

        return START_STICKY;
    }

    //@androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
