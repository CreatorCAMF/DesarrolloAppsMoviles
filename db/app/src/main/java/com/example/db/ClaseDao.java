package com.example.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClaseDao {
    @Query("SELECT * FROM clase")
    List<Clase> getAll();

    @Insert
    void insertAll(Clase... clases);

    @Update
    void update(Clase clase);

    @Delete
    void delete(Clase clase);
}
