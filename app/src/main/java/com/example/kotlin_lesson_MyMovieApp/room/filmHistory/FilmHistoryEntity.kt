package com.example.kotlin_lesson_MyMovieApp.room.filmHistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val filmTitle: String,
    val filmRating: Float,
    val releaseDate: String,
    val posterPath: String
)