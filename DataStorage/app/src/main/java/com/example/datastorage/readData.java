package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class readData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
    }

    public void deleteFile(View view){
        Context context = getApplicationContext();

        TextView txtFileName = (TextView)findViewById(R.id.txtFileName);
        String filename = txtFileName.getText().toString();

        context.deleteFile(filename);

        Log.println(Log.ASSERT,"MESSAGE","Archivo borrado");
    }

    public  void listFiles(View view){
        Context context = getApplicationContext();

        String[] files = context.fileList();

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< files.length; i++)
        {
            sb.append(files[i]+"\n");
        }
        String ls = sb.toString();
        TextView txtShowData = (TextView) findViewById(R.id.txtShowData);
        txtShowData.setText(ls);
        Log.println(Log.ASSERT,"MESSAGE",ls);
    }

    public  void readFile(View view){

        TextView txtShowData = (TextView)findViewById(R.id.txtShowData);

        Context context = getApplicationContext();

        TextView txtFileName = (TextView)findViewById(R.id.txtFileName);
        String filename = txtFileName.getText().toString();

        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
        } finally {
            String contents = stringBuilder.toString();
            Log.println(Log.ASSERT,"MESSAGE",contents);
            txtShowData.setText(contents);

        }
    }
}
