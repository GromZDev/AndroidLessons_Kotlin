package com.example.kotlin_lesson_MyMovieApp.repository.roomFavoriteRepository

import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie

interface RoomFavoriteLocalRepository {
    fun getAllFavorite(): List<Movie>
    fun saveFavoriteEntity(favoriteMovie: Movie)
}