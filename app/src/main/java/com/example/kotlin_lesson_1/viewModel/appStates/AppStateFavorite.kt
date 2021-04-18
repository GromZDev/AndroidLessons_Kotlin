package com.example.kotlin_lesson_1.viewModel.appStates

import com.example.kotlin_lesson_1.model.dto.Movie

sealed class AppStateFavorite {
    data class Success(val favoriteMovieData: List<Movie>) : AppStateFavorite()
    data class Error(val error: Throwable) : AppStateFavorite()
    object Loading : AppStateFavorite()
}