package com.example.kotlin_lesson_1.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_lesson_1.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    MainFilmFragment.newInstance()
                )
                .addToBackStack(null)
                .commit()
        }


    }
}