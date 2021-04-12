package com.example.kotlin_lesson_1.repository

import okhttp3.Callback

class FilmDetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    FilmDetailsRepository {
    override fun getFilmDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getFilmDetails(requestLink, callback)
    }
}