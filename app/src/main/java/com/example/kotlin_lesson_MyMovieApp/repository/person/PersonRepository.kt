package com.example.kotlin_lesson_MyMovieApp.repository.person

import android.util.Log
import com.example.kotlin_lesson_MyMovieApp.model.person.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PersonRepository {
    private val api: PersonAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(PersonAPI::class.java)
    }

    fun getPersonData(
        lang: String = "",
        personId: Int = 0,
        onSuccess: (person: Person) -> Unit,
        onError: () -> Unit
    ) {
        api.getWholePersonData(
            lang = lang,
            personId = personId

        )
            .enqueue(object : Callback<Person> {
                override fun onResponse(
                    call: Call<Person>,
                    response: Response<Person>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<Person>, t: Throwable) {
                    Log.e("Repository CreditForId", "onFailure", t)
                }
            })
    }
}