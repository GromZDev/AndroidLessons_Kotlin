package com.example.kotlin_lesson_1.model.category_RV

import com.example.kotlin_lesson_1.model.getPopularFilms
import java.util.*

object FilmCategoryData {

    private val random = Random()

    private val titles =  arrayListOf( "Star Wars Films", "Marvel's Films", "Top Films", "Comedy Films", "Action Films")

    private fun randomTitle() : String{
        val index = random.nextInt(titles.size)
        return titles[index]
    }

    fun getParents(count : Int) : List<FilmSpecificListCategory>{
        val parents = mutableListOf<FilmSpecificListCategory>()
        repeat(count){
            val parent = FilmSpecificListCategory(randomTitle(), getPopularFilms())
            parents.add(parent)
        }
        return parents
    }
}