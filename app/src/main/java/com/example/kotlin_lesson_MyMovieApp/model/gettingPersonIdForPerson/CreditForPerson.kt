package com.example.kotlin_lesson_MyMovieApp.model.gettingPersonIdForPerson

data class CreditForPerson (
    val credit_type: String,
    val person: PersonForId
        )

data class PersonForId(
    val name: String,
    val id: Int
)