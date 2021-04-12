package com.example.kotlin_lesson_1.utils

import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.Film
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.dto.OneFilmDTO
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import com.example.kotlin_lesson_1.model.getDefaultFilm

fun convertDtoToModel(receivedDTO: ReceivedDTO): List<FilmFeature> {
    val name = receivedDTO.original_title
    val date = receivedDTO.release_date
    val rating = receivedDTO.vote_average
    val overview = receivedDTO.overview.toString()
    val time = receivedDTO.runtime
    return listOf(FilmFeature(Film(name!!, R.drawable.film_avengers_end_game, rating!!, date!!, time!!), overview, "Default"))
}

fun convertOneFilmDtoToModel(oneFilmDTO: OneFilmDTO): List<FilmFeature> {
    val name = oneFilmDTO.original_title
    val date = oneFilmDTO.release_date
    val rating = oneFilmDTO.vote_average
    val overview = oneFilmDTO.overview.toString()
    val time = oneFilmDTO.runtime
    return listOf(FilmFeature(Film(name!!, R.drawable.film_avengers_end_game, rating!!, date!!, time!!), overview, "Default"))
}

