package com.example.kotlin_lesson_1.viewModel.oneFilmViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_1.repository.FilmDetailsRepository
import com.example.kotlin_lesson_1.repository.FilmDetailsRepositoryImpl
import com.example.kotlin_lesson_1.repository.RemoteDataSource
import com.example.kotlin_lesson_1.viewModel.AppState

class OneFilmDetailsViewModel (
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: FilmDetailsRepository =
        FilmDetailsRepositoryImpl(RemoteDataSource())
) : ViewModel()

