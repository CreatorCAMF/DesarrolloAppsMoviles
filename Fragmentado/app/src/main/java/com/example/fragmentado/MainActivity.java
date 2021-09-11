package com.example.fragmentado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (savedInstanceState == null) {

            Bundle bundle = new Bundle();
            bundle.putInt("noHorrocrux", 1);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_horrocrux, Horrocrux.class, null)
                    .commit();
        }*/
    }
}
