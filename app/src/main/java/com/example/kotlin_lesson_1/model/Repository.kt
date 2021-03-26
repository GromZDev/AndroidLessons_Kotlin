package com.example.kotlin_lesson_1.model

// Интерфейс репозитория
interface Repository {
    fun getFilmFromServer(): FilmFeature
    fun getFilmFromLocalStorage(): FilmFeature
    fun getFilmFromLocalStorageAllFilms(): List<FilmFeature>
    fun getFilmFromLocalStoragePopularFilms(): List<FilmFeature>
}