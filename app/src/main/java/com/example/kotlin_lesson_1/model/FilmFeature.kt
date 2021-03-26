package com.example.kotlin_lesson_1.model

import com.example.kotlin_lesson_1.R

data class FilmFeature(
    val film: Film = getDefaultFilm(),
    val description: String,
    val actors: String
)

fun getDefaultFilm() = Film("Мстители: Война бесконечности", 1, 8.8, 2019)

// Создал методы, возвращающие массивы фильмов:
fun getAllFilms(): List<FilmFeature> {
    return mutableListOf(
        FilmFeature(Film("Железный человек", R.drawable.iron_man, 9.1, 2009), "Первый фильм по Железного человека, снятый в уже далеком 2--9 году", "Robert Дауни Jr"),
        FilmFeature(Film("Тор: царство тьмы", R.drawable.thor_2_dark_world, 8.1, 2011), "Фильм про Тора, сына Одина.", "Крис Хемсворт"),
        FilmFeature(Film("Мстители: Война бесконечности", R.drawable.film_avengers_inf, 8.8, 2019), "Фильм про Тора, сына Одина.", "Крис Хемсворт"),
        FilmFeature(Film("Железный человек 2", R.drawable.iron_man_2, 8.8, 2019), "Фильм про Тора, сына Одина.", "Крис Хемсворт"),
        FilmFeature(Film("Тор", R.drawable.thor_1, 8.8, 2019), "Фильм про Тора, сына Одина.", "Крис Хемсворт"),
        FilmFeature(Film("Мстители", R.drawable.avengers, 8.8, 2019), "Фильм про Тора, сына Одина.", "Крис Хемсворт")

    )

}

fun getPopularFilms(): List<FilmFeature> {
    return mutableListOf(
        FilmFeature(Film("Star Wars: Episode IV", 1, 9.1, 2009), "Первый фильм по Железного человека, снятый в уже далеком 2--9 году", "Robert Дауни Jr"),
        FilmFeature(Film("Star Wars: Episode V", 1, 8.1, 2011), "Фильм про Тора, сына Одина.", "Крис Хемсворт")
    )
}
