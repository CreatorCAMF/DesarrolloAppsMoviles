package com.example.database;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;



@Dao
interface CarreraDao{
    @Query("SELECT * FROM carrera")
    ListenableFuture<List<Carrera>> selectAll();

    @Query("SELECT * FROM carrera WHERE acronimo LIKE :acronimo")
    ListenableFuture<List<Carrera>> selectByAcronimo(String acronimo);

    @Query("SELECT * FROM carrera WHERE nombre LIKE :nombre LIMIT 1")
    ListenableFuture<Carrera> selectByName(String nombre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public ListenableFuture<List<Long>> insertCarrera(List<Carrera> carreras);

    @Delete
    public ListenableFuture<Integer> delete(List<Carrera> carreras);

    @Update
    public ListenableFuture<Integer> update(List<Carrera> carreras);


    @Transaction
    @Query("SELECT * FROM carrera WHERE acronimo LIKE :acronimo")
    ListenableFuture<List<AlumnosEnCarrera>> selectAlumnosEnCarrera(String acronimo);

    @Transaction
    @Query("SELECT * FROM carrera WHERE acronimo LIKE :acronimo")
    ListenableFuture<List<ClasesDeCarrera>> selectClasesDeCarrera(String acronimo);

    @Transaction
    @Query("SELECT * FROM carrera WHERE acronimo LIKE :acronimo")
    LiveData<List<AlumnosEnClasesDeCarrera>> selectAlumnosEnClasesDeCarrera(String acronimo);
}

@Dao
interface ClaseDao{
    @Query("SELECT * FROM clase")
    ListenableFuture<List<Clase>> selectAll();

    @Query("SELECT * FROM clase WHERE clave LIKE :clave")
    ListenableFuture<Clase> selectByMatricula(String clave);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<List<Long>> insertClase(List<Clase> clases);

    @Delete
    ListenableFuture<Integer> delete(List<Clase> clase);

    @Update
    ListenableFuture<Integer> update(List<Clase> clase);

    @Transaction
    @Query("SELECT * FROM clase WHERE clave LIKE :clave")
    ListenableFuture<List<AlumnosEnClase>> selectAlumnosEnClase(String clave);
}

@Dao
interface AlumnoDao{
    @Query("SELECT * FROM alumno")
    ListenableFuture<List<Alumno>> selectAll();

    @Query("SELECT * FROM alumno WHERE matricula IN (:matriculas)")
    ListenableFuture<List<Alumno>> selectByMatricula(String[] matriculas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<List<Long>> insertAlumnos(List<Alumno> alumnos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<List<Long>> insertAlumnosEnClases(List<Alumnos_X_Clases> alumnosEnClase);

    @Delete
    ListenableFuture<Integer> delete(List<Alumno> alumno);

    @Update
    ListenableFuture<Integer> update(List<Alumno> alumno);

    @Transaction
    @Query("SELECT * FROM alumno WHERE matricula LIKE :matricula")
    ListenableFuture<ClasesDeAlumno> selectClasesDeAlumno(String matricula);
}