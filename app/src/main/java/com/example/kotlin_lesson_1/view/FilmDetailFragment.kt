package com.example.kotlin_lesson_1.view


import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin_lesson_1.databinding.FragmentFilmDetailsBinding
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.viewModel.MainViewModel


class FilmDetailFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentFilmDetailsBinding? = null // Наш binding class этого лэйаута
    private val binding get() = _binding!!
    private lateinit var mainView: View

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val film = arguments?.getParcelable<FilmFeature>(BUNDLE_EXTRA)

        if (film != null) {
            setFilmData(film)
        }

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
}