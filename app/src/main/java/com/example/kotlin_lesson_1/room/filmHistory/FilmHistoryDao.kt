package com.example.kotlin_lesson_1.room.filmHistory

import androidx.room.*

@Dao
interface FilmHistoryDao {

    @Query("SELECT * FROM FilmHistoryEntity")
    fun getAllFilmsListData(): List<FilmHistoryEntity>

    @Query("SELECT * FROM FilmHistoryEntity WHERE filmTitle LIKE :title")
    fun getFilmByTitle(title: String) : List<FilmHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Если поле существует, то игнорим
    fun insert(entity: FilmHistoryEntity)

    @Update
    fun update(entity: FilmHistoryEntity)

    @Delete
    fun delete(entity: FilmHistoryEntity)
}