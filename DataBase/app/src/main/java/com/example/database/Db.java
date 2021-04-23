package com.example.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                Alumno.class,
                Carrera.class,
                Clase.class,
                Alumnos_X_Clases.class
        },
        version = 1
)
public abstract class Db extends RoomDatabase {
    public abstract AlumnoDao alumnoDao();
    public abstract CarreraDao carreraDao();
    public abstract ClaseDao claseDao();
}
