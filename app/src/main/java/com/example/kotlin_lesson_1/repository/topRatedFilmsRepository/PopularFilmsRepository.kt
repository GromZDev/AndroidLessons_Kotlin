package com.example.kotlin_lesson_1.repository.topRatedFilmsRepository

import android.util.Log
import com.example.kotlin_lesson_1.model.dto.GetMoviesResponse
import com.example.kotlin_lesson_1.model.dto.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PopularFilmsRepository {
    private val api: PopularFilmsAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(PopularFilmsAPI::class.java)
    }

    fun getPopularMovies(
        lang: String = "",
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(lang = lang,
             page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }
}