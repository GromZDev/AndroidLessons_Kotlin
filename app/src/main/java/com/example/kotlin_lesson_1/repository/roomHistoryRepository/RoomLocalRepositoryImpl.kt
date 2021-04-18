package com.example.kotlin_lesson_1.repository.roomHistoryRepository

import com.example.kotlin_lesson_1.model.dto.Movie
import com.example.kotlin_lesson_1.room.filmHistory.FilmHistoryDao
import com.example.kotlin_lesson_1.utils.convertFilmHistoryEntityToMovie
import com.example.kotlin_lesson_1.utils.convertMovieToEntity

class RoomLocalRepositoryImpl(private val localDataSource: FilmHistoryDao) :
    RoomLocalRepository {
    override fun getAllHistory(): List<Movie> {
        return convertFilmHistoryEntityToMovie(localDataSource.getAllFilmsListData())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToEntity(movie))
    }
}