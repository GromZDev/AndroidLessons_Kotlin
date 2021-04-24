package com.example.kotlin_lesson_MyMovieApp.viewModel.oneFilmViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lesson_MyMovieApp.model.dto.OneFilmDTO
import com.example.kotlin_lesson_MyMovieApp.repository.oneFilmRepository.OneFilmDetailsRepository
import com.example.kotlin_lesson_MyMovieApp.repository.oneFilmRepository.OneFilmDetailsRepositoryImpl
import com.example.kotlin_lesson_MyMovieApp.repository.oneFilmRepository.OneFilmRemoteDataSource
import com.example.kotlin_lesson_MyMovieApp.utils.convertOneFilmDtoToModel
import com.example.kotlin_lesson_MyMovieApp.viewModel.appStates.AppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class OneFilmViewModel(
    val oneFilmLiveData: MutableLiveData<AppState> =
        MutableLiveData(),
    private val repositoryImpl: OneFilmDetailsRepository = OneFilmDetailsRepositoryImpl(
        OneFilmRemoteDataSource()
    )
) : ViewModel() {

    fun getOneFilmFromRemoteSource() {
        oneFilmLiveData.value = AppState.Loading
        repositoryImpl.getOneFilmDetailsFromServer(callBack)
    }

    private val callBack = object :
        Callback<OneFilmDTO> {
        override fun onResponse(call: Call<OneFilmDTO>, response: Response<OneFilmDTO>) {
            val serverResponse: OneFilmDTO? = response.body()
            oneFilmLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<OneFilmDTO>, t: Throwable) {
            oneFilmLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(oneFilmDTO: OneFilmDTO): AppState {
            val name = oneFilmDTO.original_title
            val date = oneFilmDTO.release_date
            val rating = oneFilmDTO.vote_average
            val overview = oneFilmDTO.overview
            val time = oneFilmDTO.runtime
            return if (name == null || date == null || rating == 0.0 || overview == null || time == 0) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertOneFilmDtoToModel(oneFilmDTO))
            }
        }

    }

}
