package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class crearArchivo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_archivo);
    }

    public void GoToAdministrador(View view){
        Intent intent = new Intent(this, administradorArchivos.class);
        startActivity(intent);
    }

    public void GuardarArchivo(View view){

        Context context =getApplicationContext();

        EditText etNombreArchivo = (EditText)findViewById(R.id.etNombreArchivo);
        String nombreArchivo = etNombreArchivo.getText().toString();
        EditText etDataArchivo = (EditText)findViewById(R.id.etDataArchivo);
        String dataArchivo = etDataArchivo.getText().toString();


        try(FileOutputStream fos = context.openFileOutput(nombreArchivo,context.MODE_PRIVATE)) {
            fos.write(dataArchivo.getBytes());
        }catch(FileNotFoundException e) {
            Log.println(Log.ASSERT,"Error Archivo", e.getMessage());
        }catch(IOException e ) {
            Log.println(Log.ASSERT,"Error IO", e.getMessage());
        }



    }
}
