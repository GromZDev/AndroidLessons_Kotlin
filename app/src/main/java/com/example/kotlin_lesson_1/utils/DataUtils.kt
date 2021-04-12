package com.example.kotlin_lesson_1.utils

import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import com.example.kotlin_lesson_1.model.getDefaultFilm

class DataUtils {

    fun convertDtoToModel(receivedDTO: ReceivedDTO): List<FilmFeature> {
        val name = receivedDTO.original_title
        val date = receivedDTO.release_date
        val rating = receivedDTO.vote_average
        val overview = receivedDTO.overview
        val time = receivedDTO.runtime
        return listOf(FilmFeature(getDefaultFilm(), "Default", "Default"))
    }

}