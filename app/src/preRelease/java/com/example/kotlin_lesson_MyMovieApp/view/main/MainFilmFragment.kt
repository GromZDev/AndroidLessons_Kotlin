package com.example.kotlin_lesson_MyMovieApp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_MyMovieApp.R
import com.example.kotlin_lesson_MyMovieApp.databinding.FragmentFilmMainBinding
import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature
import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie
import com.example.kotlin_lesson_MyMovieApp.repository.popularFilmsRepository.PopularFilmsRepository
import com.example.kotlin_lesson_MyMovieApp.view.popularFilms.PopularFilmDetailFragment
import com.example.kotlin_lesson_MyMovieApp.view.popularFilms.PopularFilmsAdapter


class MainFilmFragment : Fragment() {

    interface OnItemViewClickListener {
        fun onItemViewClick(film: FilmFeature)
    }

    interface OnPopularFilmItemViewClickListener {
        fun onPopularFilmItemViewClick(film: Movie)
    }

    private var _binding: FragmentFilmMainBinding? = null
    private val binding get() = _binding!!


    private lateinit var popularFilms: RecyclerView
    private lateinit var popularFilmsAdapter: PopularFilmsAdapter
    private lateinit var popularFilmsLayoutManager: LinearLayoutManager

    private var popularFilmsPage = 1
    private var popularFilmsLanguage = "ru"

    private lateinit var mainView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmMainBinding.inflate(inflater, container, false)
        mainView = binding.root
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularFilms = binding.filmRecyclerViewVertical
        popularFilms.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        popularFilmsLayoutManager = popularFilms.layoutManager as LinearLayoutManager
        popularFilmsAdapter =
            PopularFilmsAdapter(mutableListOf(), object : OnPopularFilmItemViewClickListener {
                override fun onPopularFilmItemViewClick(film: Movie) {
                    val manager = activity?.supportFragmentManager
                    // Если manager не null...(let)
                    manager?.let {
                        val bundle = Bundle()
                        bundle.putParcelable(PopularFilmDetailFragment.POPULAR_BUNDLE_EXTRA, film)
                        manager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                PopularFilmDetailFragment.newInstance(bundle)
                            )
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }

            })
        popularFilms.adapter = popularFilmsAdapter

        getPopularMovies()
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularFilms.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularFilmsLayoutManager.itemCount
                val visibleItemCount = popularFilmsLayoutManager.childCount
                val firstVisibleItem = popularFilmsLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularFilms.removeOnScrollListener(this)
                    popularFilmsPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun getPopularMovies() {
        PopularFilmsRepository.getPopularMovies(
            popularFilmsLanguage,
            popularFilmsPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularFilmsAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(context, "ERROR<<<<<<<<<<<<<<<<<<<<", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFilmFragment()
    }

    // Чтобы не было утечек - удаляем листенер из адаптера:
    override fun onDestroy() {
        popularFilmsAdapter.removeListener()
        super.onDestroy()
    }
}

