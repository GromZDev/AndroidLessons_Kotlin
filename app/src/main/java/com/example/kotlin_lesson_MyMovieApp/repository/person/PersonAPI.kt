package com.example.kotlin_lesson_MyMovieApp.repository.person

import com.example.kotlin_lesson_MyMovieApp.BuildConfig
import com.example.kotlin_lesson_MyMovieApp.model.person.Person
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonAPI {
    @GET("person/{person_id}")
    fun getWholePersonData(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = BuildConfig.FILM_API_KEY,
        @Query("language") lang: String
    ): Call<Person>
}