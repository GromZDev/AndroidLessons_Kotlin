package com.example.kotlin_lesson_1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

// 1 Это класс, который может переживать пересоздание активити, -> сохраняет все модели с данными
// 2 Класс ViewModel может и должен хранить все экземпляры LiveData, чтобы они переживали
// пересоздание Activity
// 3 Связь ViewModel с View осуществляется через LiveData, то есть ViewModel не управляет View
//явным образом
// 4 передаём в конструктор реализацию LiveData, поскольку сам класс LiveData абстрактный
class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> =
        MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve // Получаем LiveData. Она хранит состояние приложения

    fun getFilm() = getDataFromLocalSource() // Получаем данные


    // Метод, который имитирует запрос к БД или ещё какому-то источнику данных в приложении. Запрос
// осуществляется асинхронно в отдельном потоке. Как только поток просыпается, мы передаём в нашу
// LiveData какие-то данные через метод postValue. Если данные передаются в основном потоке,
// используйте метод setValue.
    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(3000)
            liveDataToObserve.postValue(AppState.Success(Any()))
        }.start()
    }


}