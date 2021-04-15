package com.example.kotlin_lesson_1.repository

import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.getAllFilms
import com.example.kotlin_lesson_1.model.getPopularFilms

// Имплементация интерфейса Repo
class RepositoryImpl: Repository {

// Имплементируем методы интерфейса, и в возврате делаем соответствующий список
    override fun getFilmFromLocalStorageAllFilms(): List<FilmFeature> = getAllFilms()


    override fun getFilmFromLocalStoragePopularFilms(): List<FilmFeature> = getPopularFilms()

}