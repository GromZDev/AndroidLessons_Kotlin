package com.example.kotlin_lesson_1.repository.creditForPersonRepository

import android.util.Log
import com.example.kotlin_lesson_1.model.gettingPersonIdForPerson.CreditForPerson
import com.example.kotlin_lesson_1.model.gettingPersonIdForPerson.PersonForId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CreditForPersonRepository {
    private val api: CreditForPersonAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(CreditForPersonAPI::class.java)
    }

    fun getPersonId(
        creditId: String = "",
        onSuccess: (cast: PersonForId) -> Unit,
        onError: () -> Unit
    ) {
        api.getPersonId(
            creditId = creditId
        )
            .enqueue(object : Callback<CreditForPerson> {
                override fun onResponse(
                    call: Call<CreditForPerson>,
                    response: Response<CreditForPerson>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.person)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<CreditForPerson>, t: Throwable) {
                    Log.e("Repository CreditForId", "onFailure", t)
                }
            })
    }
}