package com.example.kotlin_lesson_MyMovieApp.viewModel.popularFilmViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_MyMovieApp.app.App.Companion.getFilmFavoriteDao
import com.example.kotlin_lesson_MyMovieApp.app.App.Companion.getFilmHistoryDao
import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie
import com.example.kotlin_lesson_MyMovieApp.repository.roomFavoriteRepository.RoomFavoriteLocalRepository
import com.example.kotlin_lesson_MyMovieApp.repository.roomFavoriteRepository.RoomFavoriteLocalRepositoryImpl
import com.example.kotlin_lesson_MyMovieApp.repository.roomHistoryRepository.RoomLocalRepository
import com.example.kotlin_lesson_MyMovieApp.repository.roomHistoryRepository.RoomLocalRepositoryImpl
import com.example.kotlin_lesson_MyMovieApp.viewModel.appStates.AppStateHistory

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