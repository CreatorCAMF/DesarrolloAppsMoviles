package com.example.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CarreraDao {
    @Query("SELECT * FROM carrera")
    List<Carrera> getAll();

    @Transaction
    @Query("SELECT * FROM carrera WHERE clave LIKE :claveCarrera")
    public CarrerayAlumnos getAllAlumnosFromCarrera(String claveCarrera);

    @Insert
    void insertAll(Carrera... carreras);

    @Update
    void update(Carrera carrera);

    @Delete
    void delete(Carrera carrera);
}
