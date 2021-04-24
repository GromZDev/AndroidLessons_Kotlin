package com.example.kotlin_lesson_MyMovieApp.room.filmFavorite

import androidx.room.*

@Dao
interface FilmFavoriteDao {

    @Query("SELECT * FROM FilmFavoriteEntity")
    fun getAllFilmsListData(): List<FilmFavoriteEntity>

    @Query("SELECT * FROM FilmFavoriteEntity WHERE filmTitle LIKE :title")
    fun getFilmByTitle(title: String): List<FilmFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Если поле существует, то игнорим
    fun insert(entity: FilmFavoriteEntity)

    @Update
    fun update(entity: FilmFavoriteEntity)

    @Delete
    fun delete(entity: FilmFavoriteEntity)
}