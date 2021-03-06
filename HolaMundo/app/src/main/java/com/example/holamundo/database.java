package com.example.holamundo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

public class database extends AppCompatActivity {

    public DB db;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

        recyclerView =(RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        List<Anime> animes = new ArrayList<>();
        StringBuilder constructor = new StringBuilder();
        ListenableFuture<List<Anime>> animeFuture = db.animeDao().selectAll();
        try{
            animes = animeFuture.get();
        }catch (Exception e)
        {
            Log.println(Log.ASSERT, "Error SELECT", e.toString());
        }

        myAdapter = new animeAdapter(animes);
        recyclerView.setAdapter(myAdapter);


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


class animeAdapter extends RecyclerView.Adapter<animeAdapter.animeViewHolder>{
    public static List<Anime> ANIMES;

    public animeAdapter(List<Anime> animes){
        ANIMES = animes;
    }

    public static class animeViewHolder extends RecyclerView.ViewHolder {
        public TextView itemtvNombreAnime;
        public TextView itemtvGeneroAnime;
        public TextView itemtvAutorAnime;

        public animeViewHolder(View v){
            super(v);
            itemtvNombreAnime = (TextView)v.findViewById(R.id.itemtvNombreAnime); 
            itemtvGeneroAnime = (TextView)v.findViewById(R.id.itemtvGeneroAnime); 
            itemtvAutorAnime = (TextView)v.findViewById(R.id.itemtvAutorAnime); 
        }
    }

    @Override
    public animeAdapter.animeViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View item_anime = (View)LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anime  ,parent,false);
        animeViewHolder vh = new animeViewHolder(item_anime);
        return vh;
    }

    @Override
    public void onBindViewHolder(final animeViewHolder holder, int position)
    {
        final Anime animeItem = ANIMES.get(position);

        holder.itemtvNombreAnime.setText(animeItem.nombreAnime);

        holder.itemtvGeneroAnime.setText(animeItem.genero);

        holder.itemtvAutorAnime.setText(animeItem.autor);

        holder.itemtvNombreAnime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                Log.println(Log.ASSERT,"Element Click", "Has seleccionado la serie: "+animeItem.nombreAnime);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return ANIMES.size();
    }

}