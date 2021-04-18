package com.example.kotlin_lesson_1.app

import android.app.Application
import androidx.room.Room
import com.example.kotlin_lesson_1.room.FilmHistoryDao
import com.example.kotlin_lesson_1.room.RoomDB
import com.example.kotlin_lesson_1.room.filmFavorite.FilmFavoriteDao
import com.example.kotlin_lesson_1.room.filmFavorite.RoomFavoriteFilmDB
import java.lang.IllegalStateException

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var dataBase: RoomDB? = null
        private var dataBaseFavorite: RoomFavoriteFilmDB? = null
        private const val DB_NAME = "FilmHistory.db"
        private const val DB_FAVORITE_NAME = "FilmFavorite.db"


        fun getFilmHistoryDao(): FilmHistoryDao {
            if (dataBase == null) {
                synchronized(RoomDB::class.java) {
                    if (dataBase == null) {
                        if (appInstance == null) throw
                        IllegalStateException("My App is null while creating DataBase! <<<<<<<<<<<<")
                        dataBase = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            RoomDB::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries() // <- разрешение на главный поток
                            .build()
                    }
                }
            }
            return dataBase!!.filmHistoryDao()
        }

        fun getFilmFavoriteDao(): FilmFavoriteDao {
            if (dataBaseFavorite == null) {
                synchronized(RoomFavoriteFilmDB::class.java) {
                    if (dataBaseFavorite == null) {
                        if (appInstance == null) throw
                        IllegalStateException("My App is null while creating DataBase! <<<<<<<<<<<<")
                        dataBaseFavorite = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            RoomFavoriteFilmDB::class.java,
                            DB_FAVORITE_NAME
                        )
                            .allowMainThreadQueries() // <- разрешение на главный поток
                            .build()
                    }
                }
            }
            return dataBaseFavorite!!.filmFavoriteDao()
        }
    }
}