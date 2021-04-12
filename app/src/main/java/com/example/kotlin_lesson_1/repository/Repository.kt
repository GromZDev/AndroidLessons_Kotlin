package com.example.kotlin_lesson_1.repository

import com.example.kotlin_lesson_1.model.FilmFeature

// Интерфейс репозитория
interface Repository {

    fun getFilmFromLocalStorageAllFilms(): List<FilmFeature>
    fun getFilmFromLocalStoragePopularFilms(): List<FilmFeature>
}