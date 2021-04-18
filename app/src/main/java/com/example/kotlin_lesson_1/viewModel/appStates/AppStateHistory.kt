package com.example.kotlin_lesson_1.viewModel.appStates

import com.example.kotlin_lesson_1.model.dto.Movie

sealed class AppStateHistory {
    data class Success(val historyMovieData: List<Movie>) : AppStateHistory()
    data class Error(val error: Throwable) : AppStateHistory()
    object Loading : AppStateHistory()
}