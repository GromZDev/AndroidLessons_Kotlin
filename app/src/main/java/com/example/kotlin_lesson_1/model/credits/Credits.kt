package com.example.kotlin_lesson_1.model.credits

data class Credits(
    val id: Long,
    val cast: List<Cast>
)

data class Cast(
    val id: Long,
    val cast_id: Long,
    val credit_id: String,
    val character: String,
    val gender: Int?,
    val name: String,
    val profile_path: String?
)