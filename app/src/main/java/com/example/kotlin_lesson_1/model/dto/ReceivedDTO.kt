package com.example.kotlin_lesson_1.model.dto

data class ReceivedDTO (
    val original_title: String?,
    val release_date: String?,
    val vote_average: Double?,
    val overview: String?,
    val runtime: Int?
        )
