package com.example.kotlin_lesson_1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.Film
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import com.example.kotlin_lesson_1.repository.FilmDetailsRepository
import com.example.kotlin_lesson_1.repository.FilmDetailsRepositoryImpl
import com.example.kotlin_lesson_1.repository.RemoteDataSource
import com.example.kotlin_lesson_1.utils.convertDtoToModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"


class FilmDetailsViewModel(
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: FilmDetailsRepository =
        FilmDetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getLiveDataDetails() = detailsLiveData

    fun getFilmFromRemoteSource(requestLink: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getFilmDetailsFromServer(requestLink, callBack)
    }

    private val callBack = object : Callback {

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            val serverResponse: String? = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call, e: IOException) {
            detailsLiveData.postValue(AppState.Error(Throwable(e?.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: String): AppState {
            val receivedDTO: ReceivedDTO =
                Gson().fromJson(serverResponse, ReceivedDTO::class.java)
            val name = receivedDTO.original_title
            val date = receivedDTO.release_date
            val rating = receivedDTO.vote_average
            val overview = receivedDTO.overview
            val time = receivedDTO.runtime
            return if (name == null || date == null || rating == 0.0 || overview == null || time == 0) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(receivedDTO))
            }
        }
    }

//    private fun convertDtoToModel(receivedDTO: ReceivedDTO): List<FilmFeature> {
//        val name = receivedDTO.original_title
//        val date = receivedDTO.release_date
//        val rating = receivedDTO.vote_average
//        val overview = receivedDTO.overview.toString()
//        val time = receivedDTO.runtime
//        return listOf(FilmFeature(Film(name!!, R.drawable.film_avengers_end_game, rating!!, date!!, time!!), overview, "Default"))
//    }
}



