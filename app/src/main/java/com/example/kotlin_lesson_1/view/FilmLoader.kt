package com.example.kotlin_lesson_1.view

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class FilmLoader(private val listener: FilmLoaderListener) {

interface FilmLoaderListener {
    fun onLoaded(receivedDTO: ReceivedDTO )
    fun onFailed(throwable: Throwable)
}

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadFilm() {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/157336?api_key=637d4ef9cabbc5ece262cf072b938f1b")
            val handler = Handler(Looper.getMainLooper())
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    //   urlConnection.addRequestProperty("original_title", API_KEY)
                    urlConnection.readTimeout = 8000

                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))


                    val receivedDTO: ReceivedDTO =
                        Gson().fromJson(getLines(bufferedReader), ReceivedDTO::class.java)
                    handler.post { listener.onLoaded(receivedDTO) }
                } catch (e: Exception) {
                    Log.d("Some", "Fail in connection bro!", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                    //todo обработать ошибку
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.d("Again", "Failure in URL bro!", e)
            e.printStackTrace()
            listener.onFailed(e)
            //todo обработать ошибку
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

}