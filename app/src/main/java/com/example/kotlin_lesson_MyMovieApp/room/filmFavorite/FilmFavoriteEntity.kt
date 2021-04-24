package com.example.kotlin_lesson_MyMovieApp.room.filmFavorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmFavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val filmTitle: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val filmRating: Float,
    val releaseDate: String
)