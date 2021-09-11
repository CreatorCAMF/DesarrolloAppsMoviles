package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

public class database extends AppCompatActivity {

    public DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Context contexto = getApplicationContext();
        db = Room.databaseBuilder(contexto,DB.class,"crull-database").build();

    }

    public void InsertAnime(View view)
    {
        EditText etNombre = (EditText)findViewById(R.id.etNombre);
        EditText etGenero = (EditText)findViewById(R.id.etGenero);
        EditText etAutor = (EditText)findViewById(R.id.etAutor);

        String nombre = etNombre.getText().toString();
        String genero = etGenero.getText().toString();
        String autor = etAutor.getText().toString();

        List<Anime> animes = new ArrayList<>();
        Anime myanime = new Anime();
        myanime.nombreAnime=nombre;
        myanime.genero=genero;
        myanime.autor=autor;

        Log.println(Log.ASSERT,"Anime", myanime.nombreAnime);
        Log.println(Log.ASSERT,"Anime", myanime.genero);
        Log.println(Log.ASSERT,"Anime", myanime.autor);

        animes.add(myanime);

        Log.println(Log.ASSERT,"Anime ", ""+animes.size());

        db.animeDao().insert(animes);
    }

    public void SelectAnime(View view)
    {
        List<Anime> animes = new ArrayList<>();
        StringBuilder constructor = new StringBuilder();
        ListenableFuture<List<Anime>> animeFuture = db.animeDao().selectAll();
        try{
            animes = animeFuture.get();
            for(int i = 0; i<animes.size(); i++)
            {
                Log.println(Log.ASSERT,"DB Select", animes.get(i).nombreAnime +" "+animes.get(i).genero + " "+ animes.get(i).autor);
                constructor.append(animes.get(i).nombreAnime+"\n");

            }
        }catch (Exception e)
        {
            Log.println(Log.ASSERT, "Error SELECT", e.toString());
        }

        EditText etResult = (EditText)findViewById(R.id.etResult);
        etResult.setText(constructor.toString());
    }

    public void DeleteAnime(View view)
    {
        EditText etNombre = (EditText)findViewById(R.id.etNombre);
        EditText etGenero = (EditText)findViewById(R.id.etGenero);
        EditText etAutor = (EditText)findViewById(R.id.etAutor);

        String nombre = etNombre.getText().toString();
        String genero = etGenero.getText().toString();
        String autor = etAutor.getText().toString();

        List<Anime> animes = new ArrayList<>();
        Anime myanime = new Anime();
        myanime.nombreAnime=nombre;
        myanime.genero=genero;
        myanime.autor=autor;

        animes.add(myanime);
        db.animeDao().delete(animes);
    }

    public void UpdateAnime(View view)
    {
        EditText etNombre = (EditText)findViewById(R.id.etNombre);
        EditText etGenero = (EditText)findViewById(R.id.etGenero);
        EditText etAutor = (EditText)findViewById(R.id.etAutor);

        String nombre = etNombre.getText().toString();
        String genero = etGenero.getText().toString();
        String autor = etAutor.getText().toString();

        List<Anime> animes = new ArrayList<>();
        Anime myanime = new Anime();
        myanime.nombreAnime=nombre;
        myanime.genero=genero;
        myanime.autor=autor;

        animes.add(myanime);
        db.animeDao().update(animes);


    }
}
