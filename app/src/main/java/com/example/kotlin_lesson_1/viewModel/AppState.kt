package com.example.kotlin_lesson_1.viewModel

// Это класс, который хранит состояния приложения
sealed class AppState {
    data class Success (val cinemaData: Any): AppState() // приложение работает, данные отображаются
    data class Error (val error: Throwable): AppState() // приложение находится в состоянии загрузки данных
    object Loading: AppState() // приложении произошла какая-то ошибка

//Классы Success и Error содержат данные, поэтому их можно сделать data-классами. Loading не
//содержит данных, его можно сделать object’ом
}