package com.example.kotlin_lesson_MyMovieApp.repository.oneFilmRepository

import com.example.kotlin_lesson_MyMovieApp.model.dto.OneFilmDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Создание самого запроса через Retrofit.Builder. Запрос присваиваем переменной filmApi:
class OneFilmRemoteDataSource {
    private val filmApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(OneFilmAPI::class.java)

    fun getOneFilmDetails(callback: Callback<OneFilmDTO>) {
        filmApi.getOneFilm().enqueue(callback)
    }
}