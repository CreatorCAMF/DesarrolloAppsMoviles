package com.example.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Carrera {
    @PrimaryKey
    @NonNull
    public String clave;

    @ColumnInfo(name ="Carrera")
    public String nombre;
}
