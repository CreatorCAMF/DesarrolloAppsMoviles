package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class claveValor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave_valor);
    }

    public void SaveKeyValue(View view)
    {
        EditText etClave = (EditText)findViewById(R.id.etClave);
        String clave_name = etClave.getText().toString();
        EditText etValor = (EditText)findViewById(R.id.etValor);
        String valor = etValor.getText().toString();
        SharedPreferences clave = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = clave.edit();
        editor.putString(clave_name,valor);
        editor.commit();
    }

    public void GetKeyValue(View view)
    {
        EditText etClave = (EditText)findViewById(R.id.etClave);
        String clave_name = etClave.getText().toString();
        SharedPreferences valor= this.getPreferences(Context.MODE_PRIVATE);
        String value = valor.getString(clave_name, "No hubo exito");
        TextView tvValor = (TextView)findViewById(R.id.tvValor);
        tvValor.setText(value);
    }



}
