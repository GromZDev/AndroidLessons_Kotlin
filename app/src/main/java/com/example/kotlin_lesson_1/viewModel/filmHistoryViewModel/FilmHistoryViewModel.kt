package com.example.kotlin_lesson_1.viewModel.filmHistoryViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_1.app.App
import com.example.kotlin_lesson_1.repository.roomHistoryRepository.RoomLocalRepository
import com.example.kotlin_lesson_1.repository.roomHistoryRepository.RoomLocalRepositoryImpl
import com.example.kotlin_lesson_1.viewModel.appStates.AppStateHistory

class FilmHistoryViewModel(
    val historyLiveData: MutableLiveData<AppStateHistory> = MutableLiveData(),
    private val filmHistoryRepository: RoomLocalRepository =
        RoomLocalRepositoryImpl(App.getFilmHistoryDao())
) : ViewModel() {
    fun getAllFilmHistory() {
        historyLiveData.value = AppStateHistory.Loading
        Thread {
            Thread.sleep(1000)
            historyLiveData.postValue(AppStateHistory.Success(filmHistoryRepository.getAllHistory()))
        }.start()
    }
}