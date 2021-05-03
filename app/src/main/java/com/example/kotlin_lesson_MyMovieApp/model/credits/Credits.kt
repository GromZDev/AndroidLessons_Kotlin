package com.example.kotlin_lesson_MyMovieApp.model.credits

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Credits(
    val id: Long,
    val cast: List<Cast>
)

@Parcelize
data class Cast(
    val id: Long,
    val cast_id: Long,
    val credit_id: String,
    val character: String,
    val gender: Int?,
    val name: String,
    val profile_path: String?
): Parcelable