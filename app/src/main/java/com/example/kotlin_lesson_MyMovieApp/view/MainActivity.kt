package com.example.kotlin_lesson_MyMovieApp.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_lesson_MyMovieApp.R
import com.example.kotlin_lesson_MyMovieApp.view.filmFavorite.FilmFavoriteFragment
import com.example.kotlin_lesson_MyMovieApp.view.filmHistory.FilmHistoryFragment
import com.example.kotlin_lesson_MyMovieApp.view.gettingContacts.GettingContactsFragment
import com.example.kotlin_lesson_MyMovieApp.view.main.MainFilmFragment

class MainActivity : AppCompatActivity() {

    private val myBCReceiver = MySystemBroadcastReceiver()


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
                .commit()
        }

        registerReceiver(myBCReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(myBCReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onDestroy() {
        unregisterReceiver(myBCReceiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.fragment_container, FilmHistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_favorite -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.fragment_container, FilmFavoriteFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_get_contacts -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.fragment_container, GettingContactsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}