package com.example.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alumno {
    @PrimaryKey
    @NonNull
    public String matricula;

    @ColumnInfo(name ="nombre")
    public String name;

    @ColumnInfo(name = "anime_fav")
    public  String animeFav;

    @ColumnInfo(name ="edad")
    public int edad;

    @ColumnInfo(name = "carrera")
    public  String carrera;

}
