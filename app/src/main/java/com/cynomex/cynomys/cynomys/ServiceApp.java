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

    private Context thisContext=this;
    private LocationManager locManager;

    @Override
    public void onCreate() {

    }
    @Override
    public void onDestroy() {
        //startService(new Intent(thisContext, ServiceApp.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){
/*

        configuracion conf = new configuracion();
        //conf.insertar();
        System.out.println("*HERMI* EL SERVICIO ESTA EJECUTADO");


        */
        return START_STICKY;
    }



    ///
    private CountHandler mServiceHandler;

    private final class CountHandler extends Handler {

        private NotificationCompat.Builder mNotificationBuilder;
        private NotificationManager mNotificationManager;

        CountHandler(Looper looper) {
            super(looper);
            mNotificationBuilder =
                    new NotificationCompat.Builder(ServiceApp.this)
                            .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                            .setContentTitle(getString(R.string.notif_title_result))
                            .setAutoCancel(true);
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        @Override
        public void handleMessage(Message msg) {
           /*
            Log.i(TAG, "Current thread is " + Thread.currentThread().getName());

            Intent request = (Intent) msg.obj;
            if (ACTION_COUNT_TO.equals(request.getAction())) {
                int target = request.getIntExtra(EXTRA_COUNT_TARGET, 0);
                countTo(target);
                reportResult(target);
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
            */
        }

        private void countTo(int target) {
            for (int i=0; i<target; i++) {
                try {
                    Thread.sleep(1000);
                    Log.i(TAG, "" + (i+1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void reportResult(int target) {
            showNotification(target);
        }



        private void showNotification(int target) {
           /* mNotificationBuilder.setContentText(
                    getString(R.string.notif_content_result, target)
            );
            mNotificationManager.notify(
                    COUNT_NOTIFICATION_ID,
                    mNotificationBuilder.build()
            );
            */
        }

    }
    ///








    //@androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void insertar(){
        WebService wb = new WebService();
        wb.RegistrarUsuario2(
                "c",
                "c",
                "2018-11-11",
                1,
                "c",
                "c@mail.com");


    }








}
