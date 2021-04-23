package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class consultar extends AppCompatActivity {

    public Db DB;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        DB = Room.databaseBuilder(getApplicationContext(),
                Db.class, "database-escuela").build();
    }


    public void selecCarreras(View view){
        recyclerView = (RecyclerView) findViewById(R.id.rvData);

        recyclerView.setHasFixedSize(false);


        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false );



        recyclerView.setLayoutManager(layoutManager);

        List<Carrera> carreras = new ArrayList<Carrera>();

        ListenableFuture<List<Carrera>> carrerasListener = DB.carreraDao().selectAll();
        try {
            carreras = carrerasListener.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mAdapter = new carreraAdapter(carreras);

        recyclerView.setAdapter(mAdapter);
    }
}




class carreraAdapter extends RecyclerView.Adapter<carreraAdapter.carreraViewHolder>{

    public static List<Carrera> CARRERAS;


    public carreraAdapter(List<Carrera> carreras)
    {
        CARRERAS = carreras;
    }

    public static class carreraViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtData;
        public Button boton;
        public carreraViewHolder(View v) {
            super(v);
            txtData = (TextView) v.findViewById(R.id.txtData);
            boton =(Button)v.findViewById((R.id.button));
        }
    }


    @Override
    public carreraAdapter.carreraViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View carreraItem = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false);

        carreraViewHolder vh = new carreraViewHolder(carreraItem);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final carreraViewHolder holder, int position) {
        final Carrera carreraItem = CARRERAS.get(position);
        holder.txtData.setText(carreraItem.acronimo+": "+carreraItem.nombre);

        holder.txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.ASSERT,"MESSAGE","Pues le diste click al item de: "+carreraItem.acronimo);
            }
        });

        holder.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.ASSERT,"MESSAGE","Apretaste el bot0n de : "+carreraItem.acronimo);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return CARRERAS.size();
    }

}
