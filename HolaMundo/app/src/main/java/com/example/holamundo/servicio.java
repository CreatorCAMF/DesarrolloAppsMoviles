package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class servicio extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crearCanal();
        context= this;
        setContentView(R.layout.activity_servicio);
    }


    public void notificame(View view){
        Intent intent =new Intent(this, servicio.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent intentPendiente = PendingIntent.getActivity(this,0,intent,0);
        Log.println(Log.ASSERT,"OK", "Notificacion");
        NotificationCompat.Builder constructor = new NotificationCompat.Builder(this, "CP").
                setSmallIcon(R.drawable.ic_launcher_background).
                setContentTitle("Notificación del canal").
                setContentText("Del atlantico al pacifico").
                setPriority(NotificationCompat.PRIORITY_DEFAULT).
                setContentIntent(intentPendiente).
                setAutoCancel(true);
        NotificationManagerCompat administradorNotificacion =NotificationManagerCompat.from(this);
        administradorNotificacion.notify(117, constructor.build());
    }


    public void Servicio(View view)
    {
        Intent intent = new Intent(this, servicioIntent.class);
        startService(intent);
    }

    public void Servicio2(View view)
    {
        Intent intent = new Intent(this, servicioBackground.class);
        intent.putExtra("VALUE","Mi servicio");
        startService(intent);
    }

    public void crearCanal()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence nombre = "CanalPanama";
            String descripcion = "Del Atlantico al Pacifico";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel canalPanama = new NotificationChannel("CP",nombre,importancia);
            canalPanama.setDescription(descripcion);

            NotificationManager administrador= getSystemService(NotificationManager.class);
            administrador.createNotificationChannel(canalPanama);
            Log.println(Log.ASSERT,"OK", "Canal de Panama terminado");
        }

    }

}