package com.example.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"matricula", "clave"})
public class ClasesXAlumnos {
    @NonNull
    public String matricula;
    @NonNull
    public String clave;

}
