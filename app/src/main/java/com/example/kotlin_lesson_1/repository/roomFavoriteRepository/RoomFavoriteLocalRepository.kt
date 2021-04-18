package com.example.kotlin_lesson_1.repository.roomFavoriteRepository

import com.example.kotlin_lesson_1.model.dto.Movie

interface RoomFavoriteLocalRepository {
    fun getAllFavorite(): List<Movie>
    fun saveFavoriteEntity(favoriteMovie: Movie)
}