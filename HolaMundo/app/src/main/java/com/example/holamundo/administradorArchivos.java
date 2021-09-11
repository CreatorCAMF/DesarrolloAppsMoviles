package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class administradorArchivos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_archivos);
    }

    public void Borrar(View view){
        Context contexto = getApplicationContext();

        EditText etDeleteText = (EditText)findViewById(R.id.etDeleteFile);
        String nombreArchivo =etDeleteText.getText().toString();

        contexto.deleteFile(nombreArchivo);

        Log.println(Log.ASSERT,"Borrar archivo", "El archivo ha sido eliminado");
    }

    public void ls(View view){
        Context contexto = getApplicationContext();

        String[] archivos = contexto.fileList();

        StringBuilder constructor = new StringBuilder();

        for(int i = 0; i<archivos.length; i++)
        {
            Log.println(Log.ASSERT, "Archivo: ", archivos[i]);
            constructor.append(archivos[i]+"\n");
        }
        String listaArchivos =constructor.toString();
        TextView tvShowData = (TextView)findViewById(R.id.tvShowData);
        tvShowData.setText(listaArchivos);
    }


    public void LeerArchivo(View view){
        Context contexto = getApplicationContext();

        EditText etNombreArchivo = (EditText)findViewById(R.id.etDeleteFile);
        String nombreArchivo = etNombreArchivo.getText().toString();

        TextView tvShowData = (TextView)findViewById(R.id.tvShowData);

        try{
            FileInputStream fis =contexto.openFileInput(nombreArchivo);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder constructor = new StringBuilder();

            BufferedReader lector = new BufferedReader(isr);
            String linea= lector.readLine();

            while( linea != null)
            {
                constructor.append(linea + "\n");
                linea =lector.readLine();
            }

            String data = constructor.toString();
            tvShowData.setText(data);
        }catch (FileNotFoundException e) {
            Log.println(Log.ASSERT,"Error Archivo", e.getMessage());
        }catch (IOException e){
            Log.println(Log.ASSERT,"Error IO", e.getMessage());
        }

    }

}
