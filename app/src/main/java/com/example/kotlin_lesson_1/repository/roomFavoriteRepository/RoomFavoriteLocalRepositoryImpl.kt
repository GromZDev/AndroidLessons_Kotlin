package com.example.kotlin_lesson_1.repository.roomFavoriteRepository

import com.example.kotlin_lesson_1.model.dto.Movie
import com.example.kotlin_lesson_1.room.filmFavorite.FilmFavoriteDao
import com.example.kotlin_lesson_1.utils.convertFavoriteMovieToEntity
import com.example.kotlin_lesson_1.utils.convertFilmFavoriteEntityToMovie


class RoomFavoriteLocalRepositoryImpl(private val localFavoriteDataSource: FilmFavoriteDao) :
    RoomFavoriteLocalRepository {
    override fun getAllFavorite(): List<Movie> {
        return convertFilmFavoriteEntityToMovie(localFavoriteDataSource.getAllFilmsListData())
    }

    override fun saveFavoriteEntity(favoriteMovie: Movie) {
        localFavoriteDataSource.insert(convertFavoriteMovieToEntity(favoriteMovie))
    }
}