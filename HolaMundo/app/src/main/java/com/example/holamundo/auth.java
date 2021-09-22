package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class auth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    public void login(View view){
        EditText etMail = (EditText) findViewById(R.id.etMail);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        if(etMail.getText().toString() != "" && etPassword.getText().toString() != "")
        {
            //login(etMail.getText(),etPassword.getText());
        }
    }
}
