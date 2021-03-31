package com.example.kotlin_lesson_1.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (val filmName: String,
                 val filmImage: Int,
                 val filmRating: Double,
                 val filmYear: Int,
                 val filmTime: Int

) : Parcelable