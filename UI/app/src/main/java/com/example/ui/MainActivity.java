package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void share(View view)
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "hola");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    Intent.EXTRA_TEXT

    Log.d("myTag", intent.getStringExtra(Intent.EXTRA_TEXT));
    Log.println(Log.ASSERT,"myLog",intent.getStringExtra(Intent.EXTRA_TEXT));
}
