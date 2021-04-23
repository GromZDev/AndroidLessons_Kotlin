package com.example.kotlin_lesson_1.model.person

data class Person (
    val birthday: String?,
    val name: String,
    val also_known_as: List<KnownAs>,
    val place_of_birth: String?
        )

data class KnownAs(
    val d: List<String>
)