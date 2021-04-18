package com.example.kotlin_lesson_1.view.filmDetails

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
import com.example.kotlin_lesson_1.utils.showSnackBar
import com.example.kotlin_lesson_1.viewModel.appStates.AppState
import com.example.kotlin_lesson_1.viewModel.oneFilmViewModel.OneFilmViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_film_details.*

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null // Наш binding class этого лэйаута
    private val binding get() = _binding!!
    private lateinit var mainView: View

    private val oneFilmViewModelDetails: OneFilmViewModel by lazy {
        ViewModelProvider(this).get(OneFilmViewModel::class.java)
    }

    companion object {
        const val BUNDLE_EXTRA = "MY_Film"

        // Передаем во фрагмент бандл с данными фильма
        fun newInstance(bundle: Bundle): FilmDetailFragment {
            val fragment = FilmDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

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

//        filmsBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: FilmFeature(
//            getDefaultFilm(),
//            "Default",
//            "Default"
//        )

        oneFilmViewModelDetails.oneFilmLiveData.observe(
            viewLifecycleOwner,
            Observer { renderOneFilmData(it) })
        oneFilmViewModelDetails.getOneFilmFromRemoteSource()

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

    private fun setFilm(filmData: FilmFeature) {

        binding.twFilmName.text = filmData.film.filmName
        binding.twFilmYear.text = filmData.film.filmYear
        binding.twFilmRating.text = filmData.film.filmRating.toString()
        binding.twFilmDescription.text = filmData.description
        binding.twFilmTime.text = filmData.film.filmTime.toString()

        Picasso.get().load("https://image.tmdb.org/t/p/w500/rPrqBqZLl8m6sUQmZCchqW7IEYo.jpg")
            .into(iw_film_image)
        // Остальные поля берём штатные:
//        binding.iwFilmImage.setImageResource(filmsBundle.film.filmImage)
        binding.iwFilmImage.setColorFilter(
            Color.rgb(123, 123, 123),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

    }
}