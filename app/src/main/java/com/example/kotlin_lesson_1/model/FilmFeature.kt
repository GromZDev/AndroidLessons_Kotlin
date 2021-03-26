package com.example.kotlin_lesson_1.model

data class FilmFeature(
    val film: Film = getDefaultFilm(),
    val description: String,
    val actors: String
)

fun getDefaultFilm() = Film("Мстители: Война бесконечности", 1, 8.8, 2019)

// Создал методы, возвращающие массивы фильмов:
fun getAllFilms(): List<FilmFeature> {
    return mutableListOf(
        FilmFeature(Film("Железный человек", 1, 9.1, 2009), "Первый фильм по Железного человека, снятый в уже далеком 2--9 году", "Robert Дауни Jr"),
        FilmFeature(Film("Тор: царство тьмы", 1, 8.1, 2011), "Фильм про Тора, сына Одина.", "Крис Хемсворт")
    )

}

fun getPopularFilms(): List<FilmFeature> {
    return mutableListOf(
        FilmFeature(Film("Star Wars: Episode IV", 1, 9.1, 2009), "Первый фильм по Железного человека, снятый в уже далеком 2--9 году", "Robert Дауни Jr"),
        FilmFeature(Film("Star Wars: Episode V", 1, 8.1, 2011), "Фильм про Тора, сына Одина.", "Крис Хемсворт")
    )
}
