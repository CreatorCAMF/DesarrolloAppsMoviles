package com.example.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;


import java.util.List;


@Entity
class Carrera{

    @PrimaryKey
    @NonNull
    public String acronimo;

    @ColumnInfo(name="nombre")
    public String nombre;

}


@Entity
class Clase{

    @PrimaryKey
    @NonNull
    public String clave;

    @ColumnInfo(name="nombre")
    public String nombre;

    public String carrera;
}

@Entity
class Alumno{

    @PrimaryKey
    @NonNull
    public String matricula;

    @ColumnInfo(name="nombre")
    public String nombre;

    @ColumnInfo(name="carrera")
    public String carrera;

}


class AlumnosEnCarrera{
    @Embedded public Carrera carrera; //padre
    @Relation(
            parentColumn = "acronimo",
            entityColumn = "carrera"
    )
    public List<Alumno> alumnos; //hijoe
}


class ClasesDeCarrera{
    @Embedded public Carrera carrera;
    @Relation(
            parentColumn = "acronimo",
            entityColumn = "carrera"
    )
    public List<Clase> clases;
}

@Entity(primaryKeys = {"matricula","clave"})
class Alumnos_X_Clases{
    @NonNull
    public String matricula;
    @NonNull
    public String clave;
}


class AlumnosEnClase{
    @Embedded public Clase clase;
    @Relation(
            parentColumn = "clave",
            entityColumn = "matricula",
            associateBy = @Junction(Alumnos_X_Clases.class)
    )
    public List<Alumno> alumnos;
}

class ClasesDeAlumno{
    @Embedded public Alumno alumno;
    @Relation(
            parentColumn = "matricula",
            entityColumn = "clave",
            associateBy = @Junction(Alumnos_X_Clases.class)
    )
    public List<Clase> clases;
}

class AlumnosEnClasesDeCarrera{
    @Embedded public Carrera carrera;
    @Relation(
            entity = Clase.class,
            parentColumn = "acronimo",
            entityColumn = "carrera"
    )
    List<AlumnosEnClase> alumnosEnClases;
}