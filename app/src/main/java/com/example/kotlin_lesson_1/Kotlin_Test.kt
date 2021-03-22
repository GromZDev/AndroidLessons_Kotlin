package com.example.kotlin_lesson_1

class Kotlin_Test {

    // 1 функция обычная ============================
    fun showMessage() {
    }

    // 2 функция с возвращаемым значением ============================
    fun getName(): String {
        return "MyName"
    }

    fun getSurname(): String = "MySurname"
    fun getAge(): Int = 35
    fun getAge2() = 35

    // 3 переменные ============================
    private val name: String = "My Name" // Неизменяемая переменная
    private val surname: String = "My Surname" // Изменяемая переменная

    // 4 переменные внутри метода ============================
    fun main(){
        val obj1 = 555 // неизменяемая
        var obj2 = 999 // изменяемая
    }
}