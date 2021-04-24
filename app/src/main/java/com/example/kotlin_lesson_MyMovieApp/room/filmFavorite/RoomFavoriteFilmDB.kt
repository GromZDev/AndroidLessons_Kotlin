package com.example.kotlin_lesson_MyMovieApp.room.filmFavorite

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FilmFavoriteEntity::class], // <- массив
    version = 1,
    exportSchema = false
)
abstract class RoomFavoriteFilmDB : RoomDatabase() {
    abstract fun filmFavoriteDao(): FilmFavoriteDao
}