package com.example.kotlin_lesson_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 1 Это класс, который может переживать пересоздание активити, -> сохраняет все модели с данными
// 2 Класс ViewModel может и должен хранить все экземпляры LiveData, чтобы они переживали
// пересоздание Activity
// 3 Связь ViewModel с View осуществляется через LiveData, то есть ViewModel не управляет View
//явным образом
// 4 передаём в конструктор реализацию LiveData, поскольку сам класс LiveData абстрактный
class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> =
        MutableLiveData()
) : ViewModel() {

    fun getLiveData(): LiveData<Any> {
        return liveDataToObserve
    }


}