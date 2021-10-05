package com.example.holamundo;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class servicioIntent extends IntentService{

    public servicioIntent()
    {
        super("Servicio");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this,"Servicio de notificacion", Toast.LENGTH_LONG).show();
    return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onHandleIntent(Intent intent)
    {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
