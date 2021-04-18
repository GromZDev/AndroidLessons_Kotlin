package com.example.kotlin_lesson_1.room.filmFavorite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlin_lesson_1.room.FilmHistoryDao
import com.example.kotlin_lesson_1.room.FilmHistoryEntity

@Database(
    entities = [FilmFavoriteEntity::class], // <- массив
    version = 1,
    exportSchema = false
)
abstract class RoomFavoriteFilmDB : RoomDatabase() {
    abstract fun filmFavoriteDao(): FilmFavoriteDao
}