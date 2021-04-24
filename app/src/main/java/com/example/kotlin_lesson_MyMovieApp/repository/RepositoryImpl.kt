package com.example.kotlin_lesson_MyMovieApp.repository

import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature
import com.example.kotlin_lesson_MyMovieApp.model.getAllFilms
import com.example.kotlin_lesson_MyMovieApp.model.getPopularFilms

// Имплементация интерфейса Repo
class RepositoryImpl: Repository {

// Имплементируем методы интерфейса, и в возврате делаем соответствующий список
    override fun getFilmFromLocalStorageAllFilms(): List<FilmFeature> = getAllFilms()


    override fun getFilmFromLocalStoragePopularFilms(): List<FilmFeature> = getPopularFilms()

}