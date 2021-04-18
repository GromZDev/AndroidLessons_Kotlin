package com.example.kotlin_lesson_1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_1.app.App.Companion.getFilmFavoriteDao
import com.example.kotlin_lesson_1.app.App.Companion.getFilmHistoryDao
import com.example.kotlin_lesson_1.model.dto.Movie
import com.example.kotlin_lesson_1.repository.roomFavoriteRepository.RoomFavoriteLocalRepository
import com.example.kotlin_lesson_1.repository.roomFavoriteRepository.RoomFavoriteLocalRepositoryImpl
import com.example.kotlin_lesson_1.repository.roomHistoryRepository.RoomLocalRepository
import com.example.kotlin_lesson_1.repository.roomHistoryRepository.RoomLocalRepositoryImpl

class PopularFilmDetailsViewModel(
    val popularDetailsLiveData: MutableLiveData<AppStateHistory> = MutableLiveData(),
    private val filmHistoryRepository: RoomLocalRepository =
        RoomLocalRepositoryImpl(getFilmHistoryDao()),
    private val filmFavoriteRepository: RoomFavoriteLocalRepository =
        RoomFavoriteLocalRepositoryImpl(getFilmFavoriteDao())

) : ViewModel() {

    fun saveMovieToDB(movie: Movie) {
        filmHistoryRepository.saveEntity(movie)
    }

    fun saveFavoriteMovieToDB(movie: Movie) {
        filmFavoriteRepository.saveFavoriteEntity(movie)
    }
}