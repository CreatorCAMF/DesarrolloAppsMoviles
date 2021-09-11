package com.example.db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public AppDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();

        }
        catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

    }

    public void writeRow(View view)
    {
        writeRowCarrera(
                "Ingeniería en Sistemas Computacionales",
                "ISC");



        /*TextView txtName = (TextView)findViewById(R.id.txtNombre);
        TextView txtMatricula = (TextView)findViewById(R.id.txtMatricula);
        TextView txtAnime = (TextView)findViewById(R.id.txtSerie);
        TextView txtEdad = (TextView)findViewById(R.id.txtEdad);
        TextView txtCarrera = (TextView)findViewById(R.id.txtCarrera);
        final Alumno alumno = new Alumno();
        alumno.animeFav=txtAnime.getText().toString();
        alumno.matricula=txtMatricula.getText().toString();
        alumno.edad=Integer.parseInt(txtEdad.getText().toString());
        alumno.name=txtName.getText().toString();
        alumno.carrera = txtCarrera.getText().toString();
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    db.alumnoDao().insertAll(alumno);
                }
            });
        }
        catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }*/

    }


    public void writeRowCarrera(String nombre, String clave)
    {
        final Carrera carrera = new Carrera();
        carrera.nombre=nombre;
        carrera.clave=clave;
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    db.carreraDao().insertAll(carrera);
                }
            });
        }
        catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

    }





    public void selectAllCarrera(View view){
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    List<Carrera> carreras = db.carreraDao().getAll();
                    for (Carrera carrera:carreras) {
                        Log.println(Log.ASSERT,"MESSAGE",
                                carrera.clave+" : "+carrera.nombre);
                    }
                }
            });
            ;
        }
        catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

    }



    public void selectAll(View view){
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    List<Alumno> alumnos = db.alumnoDao().getAll();
                    for (Alumno alumno:alumnos) {
                        Log.println(Log.ASSERT,"MESSAGE",
                                "La serie favorita del alumno "+alumno.name+"("+alumno.matricula+") de "+alumno.edad+" años es " +alumno.animeFav);
                    }
                }
            });
            ;
        }
        catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

    }

    public void selectAllFrom(View view){
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    CarrerayAlumnos alumnosCarrera = db.carreraDao().getAllAlumnosFromCarrera("ISC");
                    for (Alumno alumno:alumnosCarrera.alumnos) {
                        Log.println(Log.ASSERT,"MESSAGE",
                                "La serie favorita del alumno "+alumno.name+"("+alumno.matricula+") de la carrera de "+alumnosCarrera.carrera.nombre+"  es " +alumno.animeFav);
                    }
                }
            });
            ;
        }
        catch (Exception e)
        {
            Log.println(Log.ASSERT,"ERROR",e.toString());
        }

    }
}
