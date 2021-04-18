package com.example.kotlin_lesson_1.view.popularFilms

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lesson_1.databinding.FragmentPopularFilmDetailsBinding
import com.example.kotlin_lesson_1.model.dto.Movie
import com.example.kotlin_lesson_1.viewModel.PopularFilmDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_popular_film_details.*

class PopularFilmDetailFragment : Fragment() {
    private var _binding: FragmentPopularFilmDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainView: View

    private lateinit var popularFilmsBundle: Movie

    // =============  1 Объявляем SharedPreferences и контекст для него ==============
    private lateinit var popularContext: Context
    private lateinit var sharedPref: SharedPreferences
// ===============================================================================

    private val popularFilmsViewModel: PopularFilmDetailsViewModel by lazy {
        ViewModelProvider(this).get(PopularFilmDetailsViewModel::class.java)
    }

    companion object {
        const val POPULAR_BUNDLE_EXTRA = "MY_Popular_Film"

        fun newInstance(bundle: Bundle): PopularFilmDetailFragment {
            val fragment = PopularFilmDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPopularFilmDetailsBinding.inflate(inflater, container, false)
        mainView = binding.root

// ==================  2 Инициируем преференции и берем контекст =================
        popularContext = context!!
        sharedPref = popularContext.getSharedPreferences("My-Pref", Context.MODE_PRIVATE)
// ===============================================================================
        return mainView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularFilmsBundle = arguments?.getParcelable(POPULAR_BUNDLE_EXTRA) ?: Movie(
            -1, "111", "111", "111", "111", 0.0f, "111"
        )

//        oneFilmViewModelDetails.oneFilmLiveData.observe(
//            viewLifecycleOwner,
//            Observer { renderOneFilmData(it) })
//        oneFilmViewModelDetails.getOneFilmFromRemoteSource()


// =========== 4 Берем сохранённую строку из преференций и сетим в тост ==========
        loadPreviousFilmTitleFromSharedPreferences()
// ===============================================================================

        setFilm(popularFilmsBundle)

    }

    private fun setFilm(filmData: Movie) {
        saveThisMovie(filmData)

        binding.twPopularFilmName.text = filmData.title
        binding.twPopularFilmYear.text = filmData.releaseDate
        binding.twPopularFilmRating.text = filmData.rating.toString()
        binding.twPopularFilmDescription.text = filmData.overview
        binding.twPopularFilmTime.text = filmData.id.toString()

        val imgPath: String = filmData.posterPath

        Picasso.get().load("https://image.tmdb.org/t/p/w500/$imgPath")
            .into(iw_popular_film_image)

        binding.iwPopularFilmImage.setColorFilter(
            Color.rgb(123, 123, 123),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

// =========== 3 Записываем в SharedPref тайтл просматриваемого фильма ===========
        saveFilmTitleToSharedPreferences()
// ===============================================================================
        val switch: SwitchCompat = binding.swFilmSwitch

        switch.setOnClickListener {
            saveThisMovieToFavorite(filmData)
        }


    }

    private fun saveFilmTitleToSharedPreferences() {
        val filmTitle: String = binding.twPopularFilmName.text.toString()
        with(sharedPref.edit()) {
            this?.putString("FilmTitle", "В прошлый раз Вы просматривали $filmTitle")
            this?.commit()
        }
    }

    private fun loadPreviousFilmTitleFromSharedPreferences() {
        val previousFilmTitle = sharedPref.getString("FilmTitle", "")
        if (previousFilmTitle.equals("") || previousFilmTitle == null) {
            return
        } else {
            Toast.makeText(popularContext, previousFilmTitle, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveThisMovie(movie: Movie) {
        popularFilmsViewModel.saveMovieToDB(
            Movie(
                movie.id,
                movie.title,
                movie.overview,
                movie.posterPath,
                movie.backdropPath,
                movie.rating,
                movie.releaseDate
            )
        )
    }

    private fun saveThisMovieToFavorite(favoriteMovie: Movie) {
        popularFilmsViewModel.saveFavoriteMovieToDB(
            Movie(
                favoriteMovie.id,
                favoriteMovie.title,
                favoriteMovie.overview,
                favoriteMovie.posterPath,
                favoriteMovie.backdropPath,
                favoriteMovie.rating,
                favoriteMovie.releaseDate
            )
        )

    }

}

