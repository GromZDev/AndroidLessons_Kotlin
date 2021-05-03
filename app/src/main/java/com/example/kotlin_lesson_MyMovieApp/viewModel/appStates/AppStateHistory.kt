package com.example.kotlin_lesson_MyMovieApp.viewModel.appStates

import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie

sealed class AppStateHistory {
    data class Success(val historyMovieData: List<Movie>) : AppStateHistory()
    data class Error(val error: Throwable) : AppStateHistory()
    object Loading : AppStateHistory()
}