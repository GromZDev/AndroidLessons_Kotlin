package com.example.kotlin_lesson_MyMovieApp.repository.roomFavoriteRepository

import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie
import com.example.kotlin_lesson_MyMovieApp.room.filmFavorite.FilmFavoriteDao
import com.example.kotlin_lesson_MyMovieApp.utils.convertFavoriteMovieToEntity
import com.example.kotlin_lesson_MyMovieApp.utils.convertFilmFavoriteEntityToMovie


class RoomFavoriteLocalRepositoryImpl(private val localFavoriteDataSource: FilmFavoriteDao) :
    RoomFavoriteLocalRepository {
    override fun getAllFavorite(): List<Movie> {
        return convertFilmFavoriteEntityToMovie(localFavoriteDataSource.getAllFilmsListData())
    }

    override fun saveFavoriteEntity(favoriteMovie: Movie) {
        localFavoriteDataSource.insert(convertFavoriteMovieToEntity(favoriteMovie))
    }
}