package com.example.kotlin_lesson_1.view

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlin_lesson_1.BuildConfig
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class FilmDataLoadingService(name: String = "Data Loading Service") : IntentService(name) {

    private val broadcastIntent = Intent(DATA_LOADING_INTENT_FILTER)

    // ===================== 1 Имплементируем функцию привязки =====================
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            loadFilmData()
        }
    }

    // ================== 2 Логика получения данных из ТМДБ json ===================
    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadFilmData() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/157336?api_key=" + BuildConfig.FILM_API_KEY + "&language=ru")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = "GET"
                    readTimeout = 8000
                }

                val bufferedReader =
                    BufferedReader(InputStreamReader(urlConnection.inputStream))

                val receivedDTO: ReceivedDTO =
                    Gson().fromJson(getLines(bufferedReader), ReceivedDTO::class.java)
                onResponse(receivedDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "No Data to Receive bro! <<<<<<<<<<<")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    // =============== 3 метод раскладки на строки полученных данных ===============
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

    private fun onErrorRequest(s: String) {
        putLoadedResult(FILM_DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(FILM_DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, s)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    // ======================= 4 Смотрим данные по названию ========================
    private fun onResponse(receivedDTO: ReceivedDTO) {
        val data = receivedDTO.original_title // Смотрим по названию
        if (data == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(
                receivedDTO.original_title,
                receivedDTO.overview,
                receivedDTO.release_date,
                receivedDTO.runtime,
                receivedDTO.vote_average
            )
        }
    }

    // ======== 5 если название есть, то кладем полученные данные в инстанс ========
    private fun onSuccessResponse(
        originalTitle: String?,
        overview: String?,
        releaseDate: String?,
        runtime: Int?,
        voteAverage: Double?
    ) {
        putLoadedResult(FILM_DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(FILM_TITLE_EXTRA, originalTitle)
        broadcastIntent.putExtra(FILM_OVERVIEW_EXTRA, overview)
        broadcastIntent.putExtra(FILM_DATE_EXTRA, releaseDate)
        broadcastIntent.putExtra(FILM_RUNTIME_EXTRA, runtime)
        broadcastIntent.putExtra(FILM_RATING_EXTRA, voteAverage)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadedResult(FILM_DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadedResult(FILM_DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadedResult(FILM_DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadedResult(theResult: String) {
        broadcastIntent.putExtra(FILM_DETAILS_LOADED_RESULT_EXTRA, theResult)
    }

}