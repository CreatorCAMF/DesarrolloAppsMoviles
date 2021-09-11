package com.example.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlumnoDao {
    @Query("SELECT * FROM alumno")
    List<Alumno> getAll();

    @Query("SELECT * FROM alumno WHERE matricula IN (:matriculas)")
    List<Alumno> loadAllByIds(String[] matriculas);

    @Query("SELECT * FROM alumno WHERE nombre LIKE :first AND " +
            "anime_fav LIKE :last LIMIT 1")
    Alumno findByName(String first, String last);

    @Insert
    void insertAll(Alumno... alumnos);

    @Delete
    void delete(Alumno alumno);

    @Update
    void update(Alumno alumno);

}

