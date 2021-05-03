package com.example.kotlin_lesson_MyMovieApp.repository

import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature

// Интерфейс репозитория
interface Repository {

    fun getFilmFromLocalStorageAllFilms(): List<FilmFeature>
    fun getFilmFromLocalStoragePopularFilms(): List<FilmFeature>
}