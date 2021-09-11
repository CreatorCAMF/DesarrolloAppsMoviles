package com.example.holamundo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
class Otaku{
    @PrimaryKey
    @NonNull
    public String correo;

    @ColumnInfo(name="nombre")
    public String nombre;

    @ColumnInfo(name="edad")
    public int edad;
}

@Entity
class Anime{
    @PrimaryKey
    @NonNull
    public String nombreAnime;

    @ColumnInfo(name = "genero")
    public String genero;

    @ColumnInfo(name = "autor")
    public  String autor;
}


@Entity
class Personaje{
    @PrimaryKey
    @NonNull
    public String nombrePersonaje;

    @ColumnInfo(name = "poderes")
    public String poderes;
}
