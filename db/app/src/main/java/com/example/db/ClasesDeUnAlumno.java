package com.example.db;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ClasesDeUnAlumno {
    @Embedded public Alumno alumno;
    @Relation(
            parentColumn = "matricula",
            entityColumn = "clave",
            associateBy = @Junction(ClasesXAlumnos.class)
    )
    public List<Clase> clases;
}
