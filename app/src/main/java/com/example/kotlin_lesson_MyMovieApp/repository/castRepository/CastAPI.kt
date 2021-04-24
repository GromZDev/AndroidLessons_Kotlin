package com.example.kotlin_lesson_MyMovieApp.repository.castRepository

import com.example.kotlin_lesson_MyMovieApp.BuildConfig
import com.example.kotlin_lesson_MyMovieApp.model.credits.Credits
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CastAPI {
    @GET("movie/{movie_id}/credits")
    fun getPopularMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.FILM_API_KEY,
        @Query("language") lang: String
    ): Call<Credits>
}