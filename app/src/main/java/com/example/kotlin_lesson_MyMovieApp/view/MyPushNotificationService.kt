package com.example.kotlin_lesson_MyMovieApp.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.kotlin_lesson_MyMovieApp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyPushNotificationService : FirebaseMessagingService() {

    // Он вызывается каждый раз, когда приходит уведомление и приложение открыто. Здесь проверяем,
    // пришли ли какие-то данные в уведомлении. Данные приходят в строковом формате по аналогии с
    //JSON
    override fun onMessageReceived(receivedMessage: RemoteMessage) {
        val receivedMessageData = receivedMessage.data
        if (receivedMessageData.isNotEmpty()) {
            handleDataMessage(receivedMessageData.toMap()) // Если данные есть, то переводим в Map
        }
    }

    private fun handleDataMessage(map: Map<String, String>) {
        val title = map[PUSH_KEY_TITLE]
        val message = map[PUSH_KEY_MESSAGE]
        if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
        //    Toast.makeText(applicationContext, "Данные дошли!!!", Toast.LENGTH_LONG).show()
            showPushNotification(title, message)
        }
    }

    private fun showPushNotification(title: String, message: String) {
        // Чтобы сформировать уведомление, которое хотим отобразить, используем NotificationBuilder
        val notificationBuilder = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
            // При создании уведомления мы передаём в NotificationBuilder иконку, заголовок, текст и
            // приоритет (нужен для устройств версии 7.1 и ниже) или важность (для версии 8 и выше).
            .apply {
                setSmallIcon(R.drawable.ic_movie_app)
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }
        //Чтобы показать уведомление,нужно воспользоваться системным сервисом NotificationManager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Проверяем версию операционной системы. Если она выше 26 (версия O), создаём канал.
        // Проверка необходима, потому что в старых версиях SDK такого класса нет:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        // Вызываем notify и выводим пуш-уведомление.
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
     //   Toast.makeText(applicationContext, "Push-уведомление пришло!!!", Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val  name = "Channel name"
        val descriptionText = "Channel description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    // Метод получает токен, который нужен серверу для рассылки индивидуальных уведомлений.
    // Этот метод вызывается единожды в начале работы приложения:
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        // Тут надо написать код для отправки токена на бэкэнд - сервер
    }

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 777
    }
}