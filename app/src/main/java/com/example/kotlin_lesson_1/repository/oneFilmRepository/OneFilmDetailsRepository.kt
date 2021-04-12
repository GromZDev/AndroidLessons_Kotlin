package com.example.kotlin_lesson_1.repository.oneFilmRepository

import com.example.kotlin_lesson_1.model.dto.OneFilmDTO
import okhttp3.Callback

interface OneFilmDetailsRepository {
    fun getOneFilmDetailsFromServer(callback: retrofit2.Callback<OneFilmDTO>)
}