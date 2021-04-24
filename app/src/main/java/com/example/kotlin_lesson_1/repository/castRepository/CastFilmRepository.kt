package com.example.kotlin_lesson_1.repository.castRepository

import android.util.Log
import com.example.kotlin_lesson_1.model.credits.Cast
import com.example.kotlin_lesson_1.model.credits.Credits
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CastFilmRepository {
    private val api: CastAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(CastAPI::class.java)
    }

    fun getCastList(
        lang: String = "",
        movieId: Int = 0,
        onSuccess: (cast: List<Cast>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(
            lang = lang,
            movieId = movieId
        )
            .enqueue(object : Callback<Credits> {
                override fun onResponse(
                    call: Call<Credits>,
                    response: Response<Credits>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.cast)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<Credits>, t: Throwable) {
                    Log.e("Repository Cast", "onFailure", t)
                }
            })
    }
}
