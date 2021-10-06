package com.example.holamundo;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class servicioBackground extends Service {
    private Looper servicioLopper;
    private ServiceHandler serviceHandler;
    private Context context;
    private String value;

    public servicioBackground()
    {
        context= this;
    }

    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper)
        {
            super(looper);
        }
        @Override
        public void handleMessage(Message mensaje)
        {
            Log.println(Log.ASSERT, "ok", "Handler");
            notificarStatus();
            stopSelf(mensaje.arg1);
        }
    }

    @Override
    public void onCreate(){
        HandlerThread hilo = new HandlerThread("servicio", Process.THREAD_PRIORITY_BACKGROUND);
        hilo.start();
        servicioLopper = hilo.getLooper();
        serviceHandler = new ServiceHandler(servicioLopper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        value = intent.getStringExtra("VALUE");
        Toast.makeText(this, "Servicio "+value+" Iniciado", Toast.LENGTH_SHORT).show();
        Message msg =serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return  null;
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Servicio Terminado", Toast.LENGTH_SHORT).show();
    }

    public void notificarStatus()
    {
        try
        {
            int MAX = 10;
            int MIN = 0;
            Log.println(Log.ASSERT, "ok", "Inicia ");
            Notification.Builder bob = new Notification.Builder(context, "CP")
                    .setContentText("Descargando")
                    .setContentTitle("Descarga")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setProgress(MAX,MIN,false);
            Notification anuncioStatus = bob.build();
            startForeground(100,anuncioStatus);
            while(MIN< MAX)
            {
                Thread.sleep(1000);
                MIN = MIN+1;
                bob.setProgress(MAX,MIN,false);
                anuncioStatus = bob.build();
                startForeground(100,anuncioStatus);
                Log.println(Log.ASSERT, "ok", "Corriendo " + MIN);
            }

            bob.setProgress(0,0, false);
            anuncioStatus =bob.setContentTitle("Descarga terminada").build();
            startForeground(100, anuncioStatus);
            Thread.sleep(1000);
            Log.println(Log.ASSERT, "ok", "Finaliza ");
        }catch (Exception e)
        {
            Log.println(Log.ASSERT, "ok", "Finaliza");
        }


    }
}
