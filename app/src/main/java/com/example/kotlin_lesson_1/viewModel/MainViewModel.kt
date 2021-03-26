package com.example.kotlin_lesson_1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_1.model.Repository
import com.example.kotlin_lesson_1.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> =
        MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve // Получаем LiveData. Она хранит состояние приложения

    fun getFilmFromLocalSourceAllFilms() = getDataFromLocalSource(isAllFilms = true) // Получаем данные

    fun getFilmFromLocalSourcePopularFilms() = getDataFromLocalSource(isAllFilms = false)

    fun getFilmFromRemoteSource() = getDataFromLocalSource(isAllFilms = true)

    private fun getDataFromLocalSource(isAllFilms: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(3000) // Теперь берем данные из списков:
            liveDataToObserve.postValue(AppState.Success(if (isAllFilms)
                repositoryImpl.getFilmFromLocalStorageAllFilms() else
                repositoryImpl.getFilmFromLocalStoragePopularFilms()))
        }.start()
    }


}