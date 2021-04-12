package com.example.kotlin_lesson_1.repository.oneFilmRepository

import com.example.kotlin_lesson_1.BuildConfig
import com.example.kotlin_lesson_1.model.dto.OneFilmDTO
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import retrofit2.Call
import retrofit2.http.GET

// Конкретный запрос на сервер
interface OneFilmAPI {
    @GET("/3/movie/157336?api_key=" + BuildConfig.FILM_API_KEY + "&language=ru")
    fun getOneFilm(
    ): Call<OneFilmDTO>
}