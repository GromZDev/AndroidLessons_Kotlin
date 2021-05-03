package com.example.kotlin_lesson_MyMovieApp.repository.oneFilmRepository

import com.example.kotlin_lesson_MyMovieApp.model.dto.OneFilmDTO

interface OneFilmDetailsRepository {
    fun getOneFilmDetailsFromServer(callback: retrofit2.Callback<OneFilmDTO>)
}