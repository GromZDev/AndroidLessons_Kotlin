package com.example.kotlin_lesson_MyMovieApp.model.category_RV

import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature

data class FilmSpecificListCategory (
    val title : String = "",
    val children : List<FilmFeature>
        )

