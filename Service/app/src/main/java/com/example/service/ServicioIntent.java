package com.example.service;



import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import static android.app.Service.START_STICKY;

public class ServicioIntent extends IntentService {
    public ServicioIntent() {
        super("Servicio");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Inicia tu pomodoro", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

