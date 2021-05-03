package com.example.kotlin_lesson_MyMovieApp.viewModel.appStates

import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature

// Это класс, который хранит состояния приложения
sealed class AppState {
    data class Success (val cinemaData: List<FilmFeature>): AppState()
    data class Error (val error: Throwable): AppState()
    object Loading: AppState()

}