package com.example.kotlin_lesson_1.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.kotlin_lesson_1.databinding.FragmentPopularFilmDetailsBinding
import com.example.kotlin_lesson_1.model.dto.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_popular_film_details.*

class PopularFilmDetailFragment : Fragment() {
    private var _binding: FragmentPopularFilmDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainView: View

    private lateinit var popularFilmsBundle: Movie

//    private val oneFilmViewModelDetails: OneFilmViewModel by lazy {
//        ViewModelProvider(this).get(OneFilmViewModel::class.java)
//    }


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

        setFilm(popularFilmsBundle)

    }

//    private fun renderOneFilmData(appState: AppState) {
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
//                    {
//                        oneFilmViewModelDetails.getOneFilmFromRemoteSource()
//                    })
//            }
//        }
//    }

    private fun setFilm(filmData: Movie) {

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

    }
}