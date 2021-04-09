package com.example.kotlin_lesson_1.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class MySystemBroadcastReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.toString() == "android.net.conn.CONNECTIVITY_CHANGE") {
            StringBuilder().apply {
                append("<<<<<<<< Внимание >>>>>>>>\n")
                append("Изменение статуса подключения WI-FI")
                toString().also {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
        if (intent?.action.toString() == "android.intent.action.AIRPLANE_MODE" &&
            intent?.action.toString() !== "android.net.conn.CONNECTIVITY_CHANGE"
        ) {
            StringBuilder().apply {
                append("Внимание! Изменился статус режима\n")
                append("<<<<< В самолете >>>>>")
                toString().also {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
        if (intent?.action.toString() == "android.intent.action.LOCALE_CHANGED") {
            StringBuilder().apply {
                append(" --------- Внимание --------- \n")
                append(" Вы поменяли языковые настройки ")
                toString().also {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }

        }

    }


}