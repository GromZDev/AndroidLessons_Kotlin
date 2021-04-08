package com.example.kotlin_lesson_1.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.kotlin_lesson_1.databinding.FragmentFilmDetailsBinding
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.model.dto.ReceivedDTO
import com.example.kotlin_lesson_1.model.getDefaultFilm

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null // Наш binding class этого лэйаута
    private val binding get() = _binding!!
    private lateinit var mainView: View

    private lateinit var filmsBundle: FilmFeature

    private val onLoaderListener: FilmLoader.FilmLoaderListener =
        object : FilmLoader.FilmLoaderListener {
            override fun onLoaded(receivedDTO: ReceivedDTO) {
                showFilmsDataFromTMDB(receivedDTO)
            }

            override fun onFailed(throwable: Throwable) {
                //todo обработать ошибку
            }

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

//            arguments?.getParcelable<FilmFeature>(BUNDLE_EXTRA)?.let { filmFeature ->
//                filmFeature.film.also { setFilmData(filmFeature) } }

        filmsBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: FilmFeature(
            getDefaultFilm(),
            "Default",
            "Default"
        )

        val filmLoader = FilmLoader(onLoaderListener)
        filmLoader.loadFilm()
    }


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


//================== Сетим загружаемые данные во вьюхе ==================
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


}