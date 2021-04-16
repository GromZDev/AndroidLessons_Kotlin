package com.example.kotlin_lesson_1.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FilmHistoryEntity::class], // <- массив
    version = 1,
    exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    abstract fun filmHistoryDao(): FilmHistoryDao
}