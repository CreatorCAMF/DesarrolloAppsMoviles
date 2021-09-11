package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Receptor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptor);
        Intent intent = getIntent();
        String message = intent.getStringExtra("myMessage");
        String externalMessage = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView tvCatcher = (TextView)findViewById(R.id.tvCatcher);
        tvCatcher.setText(message);
        Log.println(Log.ASSERT,"myTag","El valor que recibi es: "+externalMessage);
    }

    public void sendExternalApp(View View)
    {
        TextView tvCatcher = (TextView)findViewById(R.id.tvCatcher);
        String mensajeEnviar = "FIRMA  REMITENTE: "+tvCatcher.getText().toString();
        Intent compartirIntent = new Intent(Intent.ACTION_SEND);
        compartirIntent.setType("text/plain");
        compartirIntent.putExtra(Intent.EXTRA_TEXT,mensajeEnviar);
        startActivity(Intent.createChooser(compartirIntent,"Selecciona la app deseada"));
    }

}
