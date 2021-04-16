package com.example.kotlin_lesson_1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_1.app.App.Companion.getFilmHistoryDao
import com.example.kotlin_lesson_1.model.dto.Movie
import com.example.kotlin_lesson_1.repository.roomHistoryRepository.RoomLocalRepository
import com.example.kotlin_lesson_1.repository.roomHistoryRepository.RoomLocalRepositoryImpl

class PopularFilmDetailsViewModel(
    val popularDetailsLiveData: MutableLiveData<AppStateHistory> = MutableLiveData(),
    private val filmHistoryRepository: RoomLocalRepository =
        RoomLocalRepositoryImpl(getFilmHistoryDao())

) : ViewModel() {

    fun saveMovieToDB(movie: Movie) {
        filmHistoryRepository.saveEntity(movie)
    }
}