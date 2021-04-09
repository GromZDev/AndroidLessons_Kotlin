package com.example.kotlin_lesson_1.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_lesson_1.R

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

}