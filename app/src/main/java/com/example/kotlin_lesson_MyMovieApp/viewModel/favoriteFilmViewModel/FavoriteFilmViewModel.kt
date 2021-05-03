package com.example.kotlin_lesson_MyMovieApp.viewModel.favoriteFilmViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_MyMovieApp.app.App
import com.example.kotlin_lesson_MyMovieApp.repository.roomFavoriteRepository.RoomFavoriteLocalRepository
import com.example.kotlin_lesson_MyMovieApp.repository.roomFavoriteRepository.RoomFavoriteLocalRepositoryImpl
import com.example.kotlin_lesson_MyMovieApp.viewModel.appStates.AppStateFavorite

class FavoriteFilmViewModel(
    val favoriteFilmLiveData: MutableLiveData<AppStateFavorite> = MutableLiveData(),
    private val filmFavoriteRepository: RoomFavoriteLocalRepository =
        RoomFavoriteLocalRepositoryImpl(App.getFilmFavoriteDao())
) : ViewModel() {
    fun getAllFavoriteFilms() {
        favoriteFilmLiveData.value = AppStateFavorite.Loading
        Thread {
            Thread.sleep(1000)
            favoriteFilmLiveData.postValue(AppStateFavorite.Success(filmFavoriteRepository.getAllFavorite()))
        }.start()
    }
}
