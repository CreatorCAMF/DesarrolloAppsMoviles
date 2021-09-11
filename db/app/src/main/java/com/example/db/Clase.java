package com.example.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Clase {
    @PrimaryKey
    @NonNull
    public String clave;

    public String nombre;
}
