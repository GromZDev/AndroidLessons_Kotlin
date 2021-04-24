package com.example.kotlin_lesson_MyMovieApp.repository.oneFilmRepository

import com.example.kotlin_lesson_MyMovieApp.model.dto.OneFilmDTO

class OneFilmDetailsRepositoryImpl(private val oneFilmRemoteDataSource: OneFilmRemoteDataSource):
    OneFilmDetailsRepository {
    override fun getOneFilmDetailsFromServer(callback: retrofit2.Callback<OneFilmDTO>) {
        oneFilmRemoteDataSource.getOneFilmDetails(callback)
    }
}