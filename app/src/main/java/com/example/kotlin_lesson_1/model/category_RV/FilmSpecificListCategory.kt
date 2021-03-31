package com.example.kotlin_lesson_1.model.category_RV

import com.example.kotlin_lesson_1.model.FilmFeature

data class FilmSpecificListCategory (
    val title : String = "",
    val children : List<FilmFeature>
        )

