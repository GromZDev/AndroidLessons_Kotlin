package com.example.kotlin_lesson_1.model.repository

import okhttp3.Callback

interface FilmDetailsRepository {
    fun getFilmDetailsFromServer(requestLink: String, callback: Callback)
}