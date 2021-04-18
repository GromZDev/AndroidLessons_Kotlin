package com.example.kotlin_lesson_1.repository.roomHistoryRepository

import com.example.kotlin_lesson_1.model.dto.Movie

interface RoomLocalRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
}