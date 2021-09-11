package com.example.holamundo;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;


@Dao
interface OtakuDao{
    @Query("SELECT * FROM otaku")
    ListenableFuture<List<Otaku>> selectAll();

    @Query("SELECT * FROM otaku WHERE correo LIKE :correo LIMIT 1")
    ListenableFuture<Otaku> selectMyOtaku(String correo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public ListenableFuture<List<Long>> insert(List<Otaku> otakus);

    @Delete
    public ListenableFuture<Integer> delete(List<Otaku> otakus);

    @Update
    public ListenableFuture<Integer> update(List<Otaku> otakus);

}

@Dao
interface AnimeDao{
    @Query("SELECT * FROM anime")
    ListenableFuture<List<Anime>> selectAll();

    @Query("SELECT * FROM anime WHERE nombreAnime LIKE :nombre LIMIT 1")
    ListenableFuture<Anime> selectMyAnime(String nombre);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public ListenableFuture<List<Long>> insert(List<Anime> animes);

    @Delete
    public ListenableFuture<Integer> delete(List<Anime> animes);

    @Update
    public ListenableFuture<Integer> update(List<Anime> animes);

}

@Dao

interface PersonajeDao{
    @Query("SELECT * FROM personaje")
    ListenableFuture<List<Personaje>> selectAll();

    @Query("SELECT * FROM personaje WHERE nombrePersonaje LIKE :nombre LIMIT 1")
    ListenableFuture<Personaje> selectMyPersonaje(String nombre);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public ListenableFuture<List<Long>> insert(List<Personaje> personajes);

    @Delete
    public ListenableFuture<Integer> delete(List<Personaje> personajes);

    @Update
    public ListenableFuture<Integer> update(List<Personaje> personajes);

}
