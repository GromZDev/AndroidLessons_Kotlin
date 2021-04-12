package com.example.kotlin_lesson_1.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.databinding.FragmentFilmDetailsBinding
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.getDefaultFilm
import com.example.kotlin_lesson_1.utils.showSnackBar
import com.example.kotlin_lesson_1.view.loadingDataService.*
import com.example.kotlin_lesson_1.viewModel.AppState
import com.example.kotlin_lesson_1.viewModel.oneFilmViewModel.OneFilmViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_film_details.*

val DATA_LOADING_INTENT_FILTER = getIntentFilterKey()
val FILM_DETAILS_REQUEST_ERROR_EXTRA = getRequestError()
val FILM_DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = getRequestErrorMessage()
val FILM_DETAILS_RESPONSE_SUCCESS_EXTRA = getResponseSuccess()
val FILM_DETAILS_RESPONSE_EMPTY_EXTRA = getResponseIsEmpty()
val FILM_DETAILS_URL_MALFORMED_EXTRA = getURLMal()
val FILM_DETAILS_INTENT_EMPTY_EXTRA = getIntentIsEmpty()
val FILM_DETAILS_LOADED_RESULT_EXTRA = getLoadResult()

val FILM_TITLE_EXTRA = getTitleKeyExtra()
val FILM_OVERVIEW_EXTRA = getOverviewKeyExtra()
val FILM_DATE_EXTRA = getDateKeyExtra()
val FILM_RUNTIME_EXTRA = getRuntimeKeyExtra()
val FILM_RATING_EXTRA = getRatingKeyExtra()

private const val PROCESS_ERROR = "Обработка ошибки"

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null // Наш binding class этого лэйаута
    private val binding get() = _binding!!
    private lateinit var mainView: View

    private lateinit var filmsBundle: FilmFeature

//    private val viewModelDetails: FilmDetailsViewModel by lazy {
//        ViewModelProvider(this).get(FilmDetailsViewModel::class.java)
//    }


    private val oneFilmViewModelDetails: OneFilmViewModel by lazy {
        ViewModelProvider(this).get(OneFilmViewModel::class.java)
    }


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
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        context?.let {
//            LocalBroadcastManager.getInstance(it)
//                .registerReceiver(loadResultReceiver, IntentFilter(DATA_LOADING_INTENT_FILTER))
//        }
//    }

    // ================== Отписываем локальный Ресивер интента =====================
//    override fun onDestroy() {
//        context?.let {
//            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultReceiver)
//        }
//        super.onDestroy()
//    }
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

// ================== Получаем данные из интента (урок 6 ) =======
        //       getFilmFromIntent()
// =============================================================================

//        viewModelDetails.getLiveDataDetails()
//            .observe(viewLifecycleOwner, Observer { renderDetailsData(it) })
//        viewModelDetails.getFilmFromRemoteSource("https://api.themoviedb.org/3/movie/157336?api_key=" + BuildConfig.FILM_API_KEY + "&language=ru")

        oneFilmViewModelDetails.oneFilmLiveData.observe(viewLifecycleOwner, Observer { renderOneFilmData(it) })
        oneFilmViewModelDetails.getOneFilmFromRemoteSource()

//        getFilmFromOkHttp()
    }

    private fun renderOneFilmData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.filmDetailsFragment.visibility = View.VISIBLE
                setFilm(appState.cinemaData[0])
            }
            is AppState.Loading -> {
                binding.filmDetailsFragment.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.filmDetailsFragment.visibility = View.VISIBLE
                binding.filmDetailsFragment.showSnackBar(getString(R.string.error),
                    getString(R.string.reloading),
                    {
                        oneFilmViewModelDetails.getOneFilmFromRemoteSource()
                    })
            }
        }
    }

//    private fun renderDetailsData(appState: AppState) {
//        when (appState) {
//            is AppState.Success -> {
//                binding.filmDetailsFragment.visibility = View.VISIBLE
//                setFilm(appState.cinemaData[0])
//            }
//            is AppState.Loading -> {
//                binding.filmDetailsFragment.visibility = View.GONE
//            }
//            is AppState.Error -> {
//                binding.filmDetailsFragment.visibility = View.VISIBLE
//                binding.filmDetailsFragment.showSnackBar(getString(R.string.error),
//                    getString(R.string.reloading),
//                    { viewModelDetails.getFilmFromRemoteSource("https://api.themoviedb.org/3/movie/157336?api_key=" + BuildConfig.FILM_API_KEY + "&language=ru") })
//            }
//        }
//    }

    private fun setFilm(filmData: FilmFeature) {

        binding.twFilmName.text = filmData.film.filmName
        binding.twFilmYear.text = filmData.film.filmYear
        binding.twFilmRating.text = filmData.film.filmRating.toString()
        binding.twFilmDescription.text = filmData.description
        binding.twFilmTime.text = filmData.film.filmTime.toString()

        Picasso.get().load("https://image.tmdb.org/t/p/w500/rPrqBqZLl8m6sUQmZCchqW7IEYo.jpg").into(iw_film_image)
        // Остальные поля берём штатные:
//        binding.iwFilmImage.setImageResource(filmsBundle.film.filmImage)
        binding.iwFilmImage.setColorFilter(
            Color.rgb(123, 123, 123),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

    }


// ====================== Сетим из OkHttp =============================
//    private fun getFilmFromOkHttp() {
//        val client = OkHttpClient()
//        val builder: Request.Builder = Request.Builder()
//        builder.url("https://api.themoviedb.org/3/movie/157336?api_key=" + BuildConfig.FILM_API_KEY + "&language=ru")
//        val request: Request = builder.build()
//        val call: Call = client.newCall(request)
//        call.enqueue(object : Callback {
//            val handler: Handler = Handler(Looper.getMainLooper())
//            override fun onFailure(call: Call, e: IOException) {
//                TODO(PROCESS_ERROR)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val serverResponse: String? = response.body()?.string()
//                if (response.isSuccessful && serverResponse != null) {
//                    handler.post {
//                        renderData(Gson().fromJson(serverResponse, ReceivedDTO::class.java))
//                    }
//                } else {
//                    TODO(PROCESS_ERROR)
//                }
//            }
//        })
//
//
//    }

//    private fun renderData(receivedDTO: ReceivedDTO) {
//        val name = receivedDTO.original_title
//        val date = receivedDTO.release_date
//        val rating = receivedDTO.vote_average
//        val overview = receivedDTO.overview
//        val time = receivedDTO.runtime
//        if (name == null || date == null || rating == 0.0 || overview == null || time == 0) {
//            TODO("Обработка ошибки")
//        } else {
//            val film = filmsBundle.film
//
//            binding.twFilmName.text = name
//            binding.twFilmYear.text = date
//            binding.twFilmRating.text = rating.toString()
//            binding.twFilmDescription.text = overview
//            binding.twFilmTime.text = time.toString()
//
//            // Остальные поля берём штатные:
//            binding.iwFilmImage.setImageResource(film.filmImage)
//            binding.iwFilmImage.setColorFilter(
//                Color.rgb(123, 123, 123),
//                android.graphics.PorterDuff.Mode.MULTIPLY
//            )
//        }
//    }
// ==============================================================================

    // =================== Сетим обычнве данные из своего класса ===================
//    private fun setFilmData(filmData: FilmFeature) {
//        binding.twFilmName.text = filmData.film.filmName
//        binding.twFilmDescription.text = filmData.description
//        // Сетим скроллинг у описания. + в лэйауте android:scrollbars = "vertical":
//        binding.twFilmDescription.movementMethod = ScrollingMovementMethod()
//        binding.twFilmYear.text = filmData.film.filmYear.toString()
//        binding.twFilmRating.text = filmData.film.filmRating.toString()
//        binding.twFilmTime.text = filmData.film.filmTime.toString()
//
//        binding.iwFilmImage.setImageResource(filmData.film.filmImage)
//        // Затемняем изображение:
//        binding.iwFilmImage.setColorFilter(
//            Color.rgb(123, 123, 123),
//            android.graphics.PorterDuff.Mode.MULTIPLY
//        )
//    }
// =============================================================================

    //================== Сетим загружаемые данные json во вьюхе ================
//    private fun showFilmsDataFromTMDB(filmDTO: ReceivedDTO) {
//        with(binding) {
//            //  filmDetailsFragment.visibility = View.VISIBLE
//            val film = filmsBundle.film
//            binding.twFilmName.text = filmDTO.original_title
//            binding.twFilmYear.text = filmDTO.release_date.toString()
//            binding.twFilmRating.text = filmDTO.vote_average.toString()
//            binding.twFilmDescription.text = filmDTO.overview.toString()
//            binding.twFilmTime.text = filmDTO.runtime.toString()
//
//            // Остальные поля берём штатные:
//            binding.iwFilmImage.setImageResource(film.filmImage)
//            binding.iwFilmImage.setColorFilter(
//                Color.rgb(123, 123, 123),
//                android.graphics.PorterDuff.Mode.MULTIPLY
//            )
//        }
//    }
// =============================================================================

//=================== Получаем загружаемые данные из Интента ===================

//    private val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(p0: Context?, p1: Intent?) {
//            when (p1?.getStringExtra(FILM_DETAILS_LOADED_RESULT_EXTRA)) {
//
//                FILM_DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
//                FILM_DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
//                FILM_DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//                FILM_DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
//                FILM_DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
//
//                FILM_DETAILS_RESPONSE_SUCCESS_EXTRA -> renderDataFromIntent(
//                    ReceivedDTO(
//                        p1.getStringExtra(FILM_TITLE_EXTRA),
//                        p1.getStringExtra(FILM_DATE_EXTRA),
//                        p1.getDoubleExtra(FILM_RATING_EXTRA, 0.0),
//                        p1.getStringExtra(FILM_OVERVIEW_EXTRA),
//                        p1.getIntExtra(FILM_RUNTIME_EXTRA, 0)
//                    )
//                )
//                else -> TODO(PROCESS_ERROR)
//            }
//        }
//    }
//
//    private fun getFilmFromIntent() {
//        context?.let {
//            it.startService(Intent(it, FilmDataLoadingService::class.java))
//        }
//    }
//
//    private fun renderDataFromIntent(receivedDTO: ReceivedDTO) {
//        val name = receivedDTO.original_title
//        val date = receivedDTO.release_date
//        val rating = receivedDTO.vote_average
//        val overview = receivedDTO.overview
//        val time = receivedDTO.runtime
//        if (name == null || date == null || rating == 0.0 || overview == null || time == 0) {
//            TODO("Обработка ошибки")
//        } else {
//            val film = filmsBundle.film
//
//            binding.twFilmName.text = name
//            binding.twFilmYear.text = date
//            binding.twFilmRating.text = rating.toString()
//            binding.twFilmDescription.text = overview
//            binding.twFilmTime.text = time.toString()
//
//            // Остальные поля берём штатные:
//            binding.iwFilmImage.setImageResource(film.filmImage)
//            binding.iwFilmImage.setColorFilter(
//                Color.rgb(123, 123, 123),
//                android.graphics.PorterDuff.Mode.MULTIPLY
//            )
//        }
//    }
// =============================================================================
}