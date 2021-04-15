package com.example.kotlin_lesson_1.utils

import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.Film
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.dto.OneFilmDTO

fun convertOneFilmDtoToModel(oneFilmDTO: OneFilmDTO): List<FilmFeature> {
    val name = oneFilmDTO.original_title
    val date = oneFilmDTO.release_date
    val rating = oneFilmDTO.vote_average
    val overview = oneFilmDTO.overview.toString()
    val time = oneFilmDTO.runtime
    return listOf(FilmFeature(Film(name!!, R.drawable.film_avengers_end_game, rating!!, date!!, time!!), overview, "Default"))
}

