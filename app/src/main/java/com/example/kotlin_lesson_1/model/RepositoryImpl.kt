package com.example.kotlin_lesson_1.model

// Имплементация интерфейса Repo
class RepositoryImpl: Repository {
    override fun getFilmFromLocalStorage(): FilmFeature {
        return FilmFeature(getDefaultFilm(),"Самый кассовый фильм, с топовыми актерами", "Роберт Дауни Jr")
    }

    override fun getFilmFromServer(): FilmFeature {
        return FilmFeature(getDefaultFilm(), "Самый кассовый фильм, с топовыми актерами", "Роберт Дауни Jr")
    }
}