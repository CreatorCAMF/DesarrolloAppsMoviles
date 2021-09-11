package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InsertCarrera extends AppCompatActivity {

    public Db DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_carrera);
        DB = Room.databaseBuilder(getApplicationContext(),
                Db.class, "database-escuela").build();
    }

    public void insertCarrera(View view)
    {
        TextView txt1= (TextView)findViewById(R.id.txt1);
        TextView txt2= (TextView)findViewById(R.id.txt2);

        Carrera carrera =  new Carrera();
        carrera.acronimo=txt1.getText().toString();
        carrera.nombre = txt2.getText().toString();

        List<Carrera> carreras = new ArrayList<>();
        carreras.add(carrera);
        DB.carreraDao().insertCarrera(carreras);
    }

    public void insertClase(View view)
    {
        TextView txt1= (TextView)findViewById(R.id.txt1);
        TextView txt2= (TextView)findViewById(R.id.txt2);
        TextView txt3= (TextView)findViewById(R.id.txt3);
        Clase clase = new Clase();
        clase.clave = txt1.getText().toString();
        clase.nombre = txt2.getText().toString();
        clase.carrera = txt3.getText().toString();
        List<Clase> clases = new ArrayList<>();
        clases.add(clase);
        DB.claseDao().insertClase(clases);
    }

    public void insertAlumno(View view)
    {
        TextView txt1= (TextView)findViewById(R.id.txt1);
        TextView txt2= (TextView)findViewById(R.id.txt2);
        TextView txt3= (TextView)findViewById(R.id.txt3);
        Alumno alumno = new Alumno();
        alumno.matricula = txt1.getText().toString();
        alumno.nombre = txt2.getText().toString();
        alumno.carrera = txt3.getText().toString();
        List<Alumno> alumnos = new ArrayList<>();
        alumnos.add(alumno);
        DB.alumnoDao().insertAlumnos(alumnos);
    }

    public void inscribirAlumnoAClase(View view)
    {
        TextView txt1= (TextView)findViewById(R.id.txt1);
        TextView txt2= (TextView)findViewById(R.id.txt2);


        Alumnos_X_Clases alumnoEnClase = new Alumnos_X_Clases();


        alumnoEnClase.matricula = txt1.getText().toString();
        alumnoEnClase.clave = txt2.getText().toString();

        List<Alumnos_X_Clases> alumnosEnClases = new ArrayList<>();
        alumnosEnClases.add(alumnoEnClase);

        DB.alumnoDao().insertAlumnosEnClases(alumnosEnClases);
    }

    public void consultar(View view){
        ListenableFuture<List<Carrera>> carrerasListener = DB.carreraDao().selectAll();
        try {
            List<Carrera> carreras = carrerasListener.get();
            for (Carrera carrera:carreras) {
                Log.println(Log.ASSERT, "MESSAGE", carrera.acronimo+": "+carrera.nombre);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListenableFuture<List<Clase>> claseListener = DB.claseDao().selectAll();
        try {
            List<Clase> clases = claseListener.get();
            for (Clase clase:clases) {
                Log.println(Log.ASSERT, "MESSAGE", clase.clave+": "+clase.nombre);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListenableFuture<List<Alumno>> alumnoListener = DB.alumnoDao().selectAll();
        try {
            List<Alumno> alumnos = alumnoListener.get();
            for (Alumno alumno:alumnos) {
                Log.println(Log.ASSERT, "MESSAGE", alumno.matricula+": "+alumno.nombre + " es un "+alumno.carrera);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.println(Log.ASSERT, "MESSAGE", "Error alumno: " +e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.println(Log.ASSERT, "MESSAGE", "Error aumno: " +e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            Log.println(Log.ASSERT, "MESSAGE", "Error clases de alumnno: " +e.getMessage());
        }

        ListenableFuture<ClasesDeAlumno> clasesDeAlumnoListener = DB.alumnoDao().selectClasesDeAlumno("A01360143");
        try {
            ClasesDeAlumno clasesDelAlumno = clasesDeAlumnoListener.get();
            String nombre = clasesDelAlumno.alumno.nombre;
            for (Clase clase:clasesDelAlumno.clases) {
                Log.println(Log.ASSERT, "MESSAGE", nombre+" tiene la clase: "+clase.nombre);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.println(Log.ASSERT, "MESSAGE", "Error clases de alumno: " +e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.println(Log.ASSERT, "MESSAGE", "Error clases de alumnno: " +e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.println(Log.ASSERT, "MESSAGE", "Error clases de alumnno: " +e.getMessage());
        }

        }
    }
