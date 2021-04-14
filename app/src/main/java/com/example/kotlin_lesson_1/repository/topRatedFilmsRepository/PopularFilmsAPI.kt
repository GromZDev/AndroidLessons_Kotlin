package com.example.kotlin_lesson_1.repository.topRatedFilmsRepository

import com.example.kotlin_lesson_1.model.dto.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularFilmsAPI {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "637d4ef9cabbc5ece262cf072b938f1b",
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}