package com.example.holamundo;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(
        entities = {
                Anime.class,
                Otaku.class,
                Personaje.class
        },
        version = 1
)

public abstract class DB extends RoomDatabase {
    public abstract AnimeDao animeDao();
    public abstract OtakuDao otakuDao();
    public abstract PersonajeDao personajeDao();
}
