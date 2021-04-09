package com.example.kotlin_lesson_1.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlin_lesson_1.databinding.FragmentFilmDetailsBinding
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.dto.FilmDTO
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import com.example.kotlin_lesson_1.model.getDefaultFilm


const val DATA_LOADING_INTENT_FILTER = "FILM LOADING INTENT FILTER"
const val FILM_DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val FILM_DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val FILM_DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val FILM_TITLE_EXTRA = "TITLE_EXTRA"
const val FILM_OVERVIEW_EXTRA = "OVERVIEW_EXTRA"
const val FILM_DATE_EXTRA = "DATE_EXTRA"
const val FILM_RUNTIME_EXTRA = "RUNTIME_EXTRA"
const val FILM_RATING_EXTRA = "RATING_EXTRA"
const val FILM_DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val FILM_DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val FILM_DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val FILM_DETAILS_LOADED_RESULT_EXTRA = "LOAD RESULT"
private const val PROCESS_ERROR = "Обработка ошибки"

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null // Наш binding class этого лэйаута
    private val binding get() = _binding!!
    private lateinit var mainView: View

    private lateinit var filmsBundle: FilmFeature


// ================== Листенер для данных из json FilmLoader ===================
//    private val onLoaderListener: FilmLoader.FilmLoaderListener =
//        object : FilmLoader.FilmLoaderListener {
//            override fun onLoaded(receivedDTO: ReceivedDTO) {
//                showFilmsDataFromTMDB(receivedDTO)
//            }
//            override fun onFailed(throwable: Throwable) {
//                // обработать ошибку
//            }
//        }
// =============================================================================

    companion object {
        const val BUNDLE_EXTRA = "MY_Film"

        // Передаем во фрагмент бандл с данными фильма
        fun newInstance(bundle: Bundle): FilmDetailFragment {
            val fragment = FilmDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    // =========================== Регистрируем Ресивер ============================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultReceiver, IntentFilter(DATA_LOADING_INTENT_FILTER))
        }
    }

    // ================== Отписываем локальный Ресивер интента =====================
    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultReceiver)
        }
        super.onDestroy()
    }
// =============================================================================

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        mainView = binding.root

        return mainView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//            arguments?.getParcelable<FilmFeature>(BUNDLE_EXTRA)?.let { filmFeature ->
//                filmFeature.film.also { setFilmData(filmFeature) } }

// ================== Получаем FilmLoader для получения данных (урок 5) =======
        //   val filmLoader = FilmLoader(onLoaderListener)
        //   filmLoader.loadFilm()
// =============================================================================

        filmsBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: FilmFeature(
            getDefaultFilm(),
            "Default",
            "Default"
        )

        getFilmFromIntent()
    }


    // =================== Сетим обычнве данные из своего класса ===================
    private fun setFilmData(filmData: FilmFeature) {
        binding.twFilmName.text = filmData.film.filmName
        binding.twFilmDescription.text = filmData.description
        // Сетим скроллинг у описания. + в лэйауте android:scrollbars = "vertical":
        binding.twFilmDescription.movementMethod = ScrollingMovementMethod()
        binding.twFilmYear.text = filmData.film.filmYear.toString()
        binding.twFilmRating.text = filmData.film.filmRating.toString()
        binding.twFilmTime.text = filmData.film.filmTime.toString()

        binding.iwFilmImage.setImageResource(filmData.film.filmImage)
        // Затемняем изображение:
        binding.iwFilmImage.setColorFilter(
            Color.rgb(123, 123, 123),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
    }
// =============================================================================

    //================== Сетим загружаемые данные json во вьюхе ================
    private fun showFilmsDataFromTMDB(filmDTO: ReceivedDTO) {
        with(binding) {
            //  filmDetailsFragment.visibility = View.VISIBLE
            val film = filmsBundle.film
            binding.twFilmName.text = filmDTO.original_title
            binding.twFilmYear.text = filmDTO.release_date.toString()
            binding.twFilmRating.text = filmDTO.vote_average.toString()
            binding.twFilmDescription.text = filmDTO.overview.toString()
            binding.twFilmTime.text = filmDTO.runtime.toString()

            // Остальные поля берём штатные:
            binding.iwFilmImage.setImageResource(film.filmImage)
            binding.iwFilmImage.setColorFilter(
                Color.rgb(123, 123, 123),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
    }
// =============================================================================

//=================== Получаем загружаемые данные из Интента ===================

    private val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1?.getStringExtra(FILM_DETAILS_LOADED_RESULT_EXTRA)) {

                FILM_DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                FILM_DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                FILM_DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                FILM_DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                FILM_DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)

                FILM_DETAILS_RESPONSE_SUCCESS_EXTRA -> renderDataFromIntent(
                    ReceivedDTO(
                        p1.getStringExtra(FILM_TITLE_EXTRA),
                        p1.getStringExtra(FILM_DATE_EXTRA),
                        p1.getDoubleExtra(FILM_RATING_EXTRA, 0.0),
                        p1.getStringExtra(FILM_OVERVIEW_EXTRA),
                        p1.getIntExtra(FILM_RUNTIME_EXTRA, 0)
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    private fun getFilmFromIntent() {
        context?.let {
            it.startService(Intent(it, FilmDataLoadingService::class.java))
        }
    }

    private fun renderDataFromIntent(receivedDTO: ReceivedDTO) {
        val name = receivedDTO.original_title
        val date = receivedDTO.release_date
        val rating = receivedDTO.vote_average
        val overview = receivedDTO.overview
        val time = receivedDTO.runtime
        if (name == null || date == null || rating == 0.0 || overview == null || time == 0) {
            TODO("Обработка ошибки")
        } else {
            val film = filmsBundle.film

            binding.twFilmName.text = name
            binding.twFilmYear.text = date
            binding.twFilmRating.text = rating.toString()
            binding.twFilmDescription.text = overview
            binding.twFilmTime.text = time.toString()

            // Остальные поля берём штатные:
            binding.iwFilmImage.setImageResource(film.filmImage)
            binding.iwFilmImage.setColorFilter(
                Color.rgb(123, 123, 123),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
    }
// =============================================================================
}