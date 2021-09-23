
package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar abMain = (Toolbar) findViewById(R.id.abMain);
        setSupportActionBar(abMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.abbtnArchivos:
                goToCrearArchivo();
                return true;
            case R.id.abbtnClaveValor:
                goToClaveValor();
                return true;
            case R.id.abbtnDB:
                goToDB();
                return true;
            case R.id.abbtnRegistro:
                goToRegistro();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void calculateSum(View view)
    {
        EditText etArg1 = (EditText)findViewById(R.id.etArg1);
        EditText etARg2 = (EditText)findViewById(R.id.etArg2);
        TextView tvResult = (TextView)findViewById(R.id.tvResult);
        String SetArg1 = etArg1.getText().toString();
        String SetArg2 = etARg2.getText().toString();
        int IetArg1 = Integer.parseInt(SetArg1);
        int IetArg2 = Integer.parseInt(SetArg2);
        int ItvResult = IetArg1+IetArg2;
        tvResult.setText(ItvResult+"");
    }

    public void enviarData(View view)
    {
        TextView tvResult = (TextView)findViewById(R.id.tvResult);
        String message = tvResult.getText().toString();
        Intent intent = new Intent(this, Receptor.class);
        intent.putExtra("myMessage",message);
        startActivity(intent);
    }

    public void goToClaveValor()
    {
        Intent intent = new Intent(this, claveValor.class);
        startActivity(intent);
    }

    public void goToCrearArchivo()
    {
        Intent intent = new Intent(this, crearArchivo.class);
        startActivity(intent);
    }

    public void goToDB()
    {
        Intent intent = new Intent(this, database.class);
        startActivity(intent);
    }

    public void goToRegistro()
    {
        Intent intent = new Intent(this, auth.class);
        startActivity(intent);
    }




}
