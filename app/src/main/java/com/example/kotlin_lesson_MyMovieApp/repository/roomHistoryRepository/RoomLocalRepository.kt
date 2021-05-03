package com.example.kotlin_lesson_MyMovieApp.repository.roomHistoryRepository

import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie

interface RoomLocalRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
}