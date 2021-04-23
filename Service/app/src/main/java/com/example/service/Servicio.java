package com.example.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import android.os.Process;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Servicio extends Service {
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    private Context context;
    private String TIEMPO;


    public Servicio(){
        context = this;
    }


    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);

        }
        @Override
        public void handleMessage(Message msg) {
            notificacionBarraProgreso();
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TIEMPO = intent.getStringExtra("DURACION");
        Toast.makeText(this, "Servicio Iniciado cuenta " +TIEMPO+" segundos ", Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "Servicio Iniciado cuenta " , Toast.LENGTH_SHORT).show();

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);



        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Servicio terminado", Toast.LENGTH_SHORT).show();
    }


    public void notificacionToast()
    {
        try {
            Intent notificationIntent = new Intent(context, Servicio.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context, 0, notificationIntent, 0);

            Notification notification =
                    new Notification.Builder(context, "CANAL_MIO")
                            .setContentTitle("Anuncio")
                            .setContentText("Info del servicio")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentIntent(pendingIntent)
                            .setTicker("117")
                            .build();

            startForeground(1,notification);

            Thread.sleep(5000);


            int t = Integer.parseInt(TIEMPO);
            Thread.sleep(t*1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    public void notificacionBarraProgreso()
    {
        try {


            int MAX = Integer.parseInt(TIEMPO);
            int MIN = 0;

            Notification.Builder builder = new Notification.Builder(context, "CANAL_MIO")
                    .setContentTitle("Pomodoro en progreso")
                    .setContentText("Vamos tu puedes")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setProgress(MAX, MIN, false);

            Notification noticia = builder.build();

            startForeground(100,noticia);

            while(MIN < MAX){
                Thread.sleep(1000);
                MIN ++;
                builder.setProgress(MAX, MIN, false);
                noticia = builder.build();
                startForeground(100,noticia);
            }

            builder.setProgress(0, 0, false);
            noticia = builder.setContentText("Pomodoro completado").build();
            startForeground(100,noticia);


        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}