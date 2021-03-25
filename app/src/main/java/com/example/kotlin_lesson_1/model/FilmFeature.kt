package com.example.kotlin_lesson_1.model

data class FilmFeature(
    val film: Film = getDefaultFilm(),
    val description: String,
    val actors: String
)

fun getDefaultFilm() = Film("Мстители: Война бесконечности", 1, 8.8, 2019)


