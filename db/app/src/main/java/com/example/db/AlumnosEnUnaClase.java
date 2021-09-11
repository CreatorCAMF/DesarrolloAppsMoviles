package com.example.db;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class AlumnosEnUnaClase {
    @Embedded public Clase clase;
    @Relation(
            parentColumn = "clave",
            entityColumn = "matricula",
            associateBy = @Junction(ClasesXAlumnos.class)
    )
    public List<Alumno> alumnos;
}
