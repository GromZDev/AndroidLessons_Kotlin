package com.example.kotlin_lesson_MyMovieApp.repository.creditForPersonRepository

import com.example.kotlin_lesson_MyMovieApp.BuildConfig
import com.example.kotlin_lesson_MyMovieApp.model.gettingPersonIdForPerson.CreditForPerson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CreditForPersonAPI {
    @GET("credit/{credit_id}")
    fun getPersonId(
        @Path("credit_id") creditId: String,
        @Query("api_key") apiKey: String = BuildConfig.FILM_API_KEY
    ): Call<CreditForPerson>
}