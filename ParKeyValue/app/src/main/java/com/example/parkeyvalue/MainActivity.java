package com.example.parkeyvalue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveSearchValues(View view){
        TextView txtClave = (TextView)findViewById(R.id.txtClave);
        TextView txtValor = (TextView)findViewById(R.id.txtValor);
        String clave = txtClave.getText().toString();
        String valor = txtValor.getText().toString();


        if(valor.isEmpty())
        {
            searchKey(clave);
            Log.println(Log.ASSERT,"MESSAGE","Campo Vacio");
        }
        else{
            saveKeyValue(clave,valor);
            Log.println(Log.ASSERT,"MESSAGE","Campo con valor");
        }
    }

    private void searchKey(String clave){

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String highScore = sharedPref.getString(clave, "Default");
        TextView txtVal = (TextView)findViewById(R.id.txtValor);
        txtVal.setText(highScore);

    }

    private void saveKeyValue(String clave, String valor){

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(clave,valor);
        editor.commit();
    }
}
