package com.example.kotlin_lesson_MyMovieApp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_MyMovieApp.repository.Repository
import com.example.kotlin_lesson_MyMovieApp.repository.RepositoryImpl
import com.example.kotlin_lesson_MyMovieApp.viewModel.appStates.AppState
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> =
        MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve // Получаем LiveData. Она хранит состояние приложения

    fun getFilmFromLocalSourceAllFilms() = getDataFromLocalSource(isAllFilms = true) // Получаем данные

    fun getFilmFromLocalSourcePopularFilms() = getDataFromLocalSource(isAllFilms = false)

    private fun getDataFromLocalSource(isAllFilms: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000) // Теперь берем данные из списков:
            liveDataToObserve.postValue(
                AppState.Success(if (isAllFilms)
                repositoryImpl.getFilmFromLocalStorageAllFilms() else
                repositoryImpl.getFilmFromLocalStoragePopularFilms()))
        }.start()
    }


}