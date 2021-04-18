package com.example.kotlin_lesson_1.utils

import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.Film
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.dto.Movie
import com.example.kotlin_lesson_1.model.dto.OneFilmDTO
import com.example.kotlin_lesson_1.room.FilmHistoryEntity
import com.example.kotlin_lesson_1.room.filmFavorite.FilmFavoriteEntity

fun convertOneFilmDtoToModel(oneFilmDTO: OneFilmDTO): List<FilmFeature> {
    val name = oneFilmDTO.original_title
    val date = oneFilmDTO.release_date
    val rating = oneFilmDTO.vote_average
    val overview = oneFilmDTO.overview.toString()
    val time = oneFilmDTO.runtime
    return listOf(FilmFeature(Film(name!!, R.drawable.film_avengers_end_game, rating!!, date!!, time!!), overview, "Default"))
}

// Для списка истории посещений фильмов ===
fun convertFilmHistoryEntityToMovie(entityList: List<FilmHistoryEntity>) : List<Movie> {
    return entityList.map {
        Movie(it.id, it.filmTitle, "Default Overview", it.posterPath,
            "Default Backdrop Path", it.filmRating, it.releaseDate )
    }
}

fun convertMovieToEntity(movie: Movie) : FilmHistoryEntity {
    return FilmHistoryEntity(movie.id, movie.title, movie.rating,
        movie.releaseDate, movie.posterPath)
}
// ========================================

// Для списка любимых фильмов
fun convertFilmFavoriteEntityToMovie(entityList: List<FilmFavoriteEntity>) : List<Movie> {
    return entityList.map {
        Movie(it.id, it.filmTitle, it.overview, it.posterPath,
            it.backdropPath, it.filmRating, it.releaseDate )
    }
}

fun convertFavoriteMovieToEntity(movie: Movie) : FilmFavoriteEntity {
    return FilmFavoriteEntity(movie.id, movie.title, movie.overview,
        movie.posterPath, movie.backdropPath, movie.rating, movie.releaseDate)
}
// ========================================
