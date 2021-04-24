package com.example.kotlin_lesson_MyMovieApp.repository.popularFilmsRepository

import com.example.kotlin_lesson_MyMovieApp.BuildConfig
import com.example.kotlin_lesson_MyMovieApp.model.dto.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularFilmsAPI {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.FILM_API_KEY,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}