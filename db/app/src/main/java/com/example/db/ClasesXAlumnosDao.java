package com.example.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ClasesXAlumnosDao {

    @Insert
    void insertAll(ClasesXAlumnos... clasesXAlumnos);

    @Transaction
    @Query("SELECT * FROM Alumno")
    public List<ClasesDeUnAlumno> getClasesDeAlumno();

    @Transaction
    @Query("SELECT * FROM Clase")
    public List<AlumnosEnUnaClase> getAlumnosEnUnaClase();


}
