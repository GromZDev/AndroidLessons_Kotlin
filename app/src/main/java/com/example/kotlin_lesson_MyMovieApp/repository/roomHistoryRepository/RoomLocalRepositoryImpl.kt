package com.example.kotlin_lesson_MyMovieApp.repository.roomHistoryRepository

import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie
import com.example.kotlin_lesson_MyMovieApp.room.filmHistory.FilmHistoryDao
import com.example.kotlin_lesson_MyMovieApp.utils.convertFilmHistoryEntityToMovie
import com.example.kotlin_lesson_MyMovieApp.utils.convertMovieToEntity

class RoomLocalRepositoryImpl(private val localDataSource: FilmHistoryDao) :
    RoomLocalRepository {
    override fun getAllHistory(): List<Movie> {
        return convertFilmHistoryEntityToMovie(localDataSource.getAllFilmsListData())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToEntity(movie))
    }
}