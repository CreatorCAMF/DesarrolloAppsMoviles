package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static android.os.storage.StorageManager.ACTION_MANAGE_STORAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getNextActivity(View view)
    {
        Intent intent = new Intent(this, readData.class);
        startActivity(intent);
    }

    public void createFile(View view){

        Context context = getApplicationContext();

        TextView txtBytes = (TextView)findViewById(R.id.txtBytes);

        String filename = "myfile"+txtBytes.getText().toString();

        TextView txtData = (TextView)findViewById(R.id.txtData);

        String data = txtData.getText().toString();

        try (FileOutputStream fos = context.openFileOutput(filename, context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void checkFreeSpace(View view){
        // App needs 10 MB within internal storage.

        TextView txtBytes = (TextView)findViewById(R.id.txtBytes);

        long space = Long.parseLong(txtBytes.getText().toString());

        long NUM_BYTES_NEEDED_FOR_MY_APP = 1024 * 1024 * space;

        StorageManager storageManager =
                getApplicationContext().getSystemService(StorageManager.class);

        UUID appSpecificInternalDirUuid = null;

        try {
            appSpecificInternalDirUuid = storageManager.getUuidForPath(getFilesDir());
        }catch (Exception e){
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

        long availableBytes = 0;

        try {
            availableBytes= storageManager.getAllocatableBytes(appSpecificInternalDirUuid);
        }catch (Exception e){
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

        Log.println(Log.ASSERT,"MESSAGE","El numero de bytes disponble es de: " + availableBytes);

        if (availableBytes >= NUM_BYTES_NEEDED_FOR_MY_APP) {
            try {
                storageManager.allocateBytes(appSpecificInternalDirUuid, NUM_BYTES_NEEDED_FOR_MY_APP);
                Log.println(Log.ASSERT,"MESSAGE","Memoria obtenida");
            }catch (Exception e){
                Log.println(Log.ASSERT,"ERROR",e.toString());

            }

        } else {
            Log.println(Log.ASSERT,"MESSAGE","No hay suficiente memoria");
        }

    }
}
