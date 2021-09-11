package com.example.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Alumno.class, Carrera.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    public abstract AlumnoDao alumnoDao();
    public abstract CarreraDao carreraDao();
}
