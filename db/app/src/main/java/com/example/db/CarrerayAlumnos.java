package com.example.db;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CarrerayAlumnos {
    @Embedded public Carrera carrera;
    @Relation(
            parentColumn = "clave",
            entityColumn = "carrera"
    )
    public List<Alumno> alumnos;
}
