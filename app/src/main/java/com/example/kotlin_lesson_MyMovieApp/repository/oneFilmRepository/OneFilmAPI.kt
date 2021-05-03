package com.example.kotlin_lesson_MyMovieApp.repository.oneFilmRepository

import com.example.kotlin_lesson_MyMovieApp.BuildConfig
import com.example.kotlin_lesson_MyMovieApp.model.dto.OneFilmDTO
import retrofit2.Call
import retrofit2.http.GET

// Конкретный запрос на сервер
interface OneFilmAPI {
    @GET("/3/movie/157336?api_key=" + BuildConfig.FILM_API_KEY + "&language=ru")
    fun getOneFilm(
    ): Call<OneFilmDTO>
}