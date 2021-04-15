package com.example.kotlin_lesson_1.repository.oneFilmRepository

import com.example.kotlin_lesson_1.model.dto.OneFilmDTO

class OneFilmDetailsRepositoryImpl(private val oneFilmRemoteDataSource: OneFilmRemoteDataSource):
    OneFilmDetailsRepository {
    override fun getOneFilmDetailsFromServer(callback: retrofit2.Callback<OneFilmDTO>) {
        oneFilmRemoteDataSource.getOneFilmDetails(callback)
    }
}