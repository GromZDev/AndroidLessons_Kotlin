package com.example.kotlin_lesson_MyMovieApp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_MyMovieApp.R
import com.example.kotlin_lesson_MyMovieApp.databinding.FragmentFilmMainBinding
import com.example.kotlin_lesson_MyMovieApp.model.category_RV.FilmCategoryData
import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature
import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie
import com.example.kotlin_lesson_MyMovieApp.repository.popularFilmsRepository.PopularFilmsRepository
import com.example.kotlin_lesson_MyMovieApp.utils.showSnackBar
import com.example.kotlin_lesson_MyMovieApp.view.popularFilms.PopularFilmDetailFragment
import com.example.kotlin_lesson_MyMovieApp.view.category_RV.FilmCategoryAdapter
import com.example.kotlin_lesson_MyMovieApp.view.popularFilms.PopularFilmsAdapter
import com.example.kotlin_lesson_MyMovieApp.viewModel.appStates.AppState
import com.example.kotlin_lesson_MyMovieApp.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar


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

    // viewModel создаем через делегирование посредством by через функцию lazy.
    // Модель будет создана только тогда, когда к ней впервые обратятся, или не будет создана,
    // если к ней так никто и не обратится. Экономим ресурсы!
    private val mainFilmsViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

//    private val adapter = MainFilmFragmentAdapter(object : OnItemViewClickListener {
//        override fun onItemViewClick(film: FilmFeature) {
//            val manager = activity?.supportFragmentManager
//            // Если manager не null...(let)
//            manager?.let {
//                val bundle = Bundle()
//                bundle.putParcelable(FilmDetailFragment.BUNDLE_EXTRA, film)
//                manager.beginTransaction()
//                    .replace(R.id.fragment_container, FilmDetailFragment.newInstance(bundle))
//                    .addToBackStack("")
//                    .commitAllowingStateLoss()
//            }
//        }
//
//    }

//    )
    private var isFilmSetAll: Boolean = true

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
//        binding.filmRecyclerViewVertical.adapter = adapter
        binding.buttonChangeFilmCategory.setOnClickListener {
            changeFilmDataInMainFragment()
        }

        mainFilmsViewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        mainFilmsViewModel.getFilmFromLocalSourceAllFilms()


        popularFilms = binding.filmRecyclerViewVertical
        popularFilms.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        popularFilmsLayoutManager = popularFilms.layoutManager as LinearLayoutManager
        popularFilmsAdapter = PopularFilmsAdapter(mutableListOf(), object : OnPopularFilmItemViewClickListener {
            override fun onPopularFilmItemViewClick(film: Movie) {
                val manager = activity?.supportFragmentManager
                // Если manager не null...(let)
                manager?.let {
                    val bundle = Bundle()
                    bundle.putParcelable(PopularFilmDetailFragment.POPULAR_BUNDLE_EXTRA, film)
                    manager.beginTransaction()
                        .replace(R.id.fragment_container, PopularFilmDetailFragment.newInstance(bundle))
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


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.filmRecyclerViewVertical.visibility = View.VISIBLE
                binding.twPartName.visibility = View.VISIBLE
                initCategoryRecyclerView()
             //   adapter.setFilms(appState.cinemaData)

                binding.mainFragmentView.showSnackBarForSuccess(
                    getString(R.string.successData), 5000,
                    { setColorSbBG() },
                    { setTextSbColor(ContextCompat.getColor(context, R.color.item_rv_bg)) }
                )
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                binding.filmRecyclerViewVertical.visibility = View.GONE
                binding.twPartName.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.mainFragmentView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reloading),
                    { mainFilmsViewModel.getFilmFromLocalSourceAllFilms() }
                )
            }
        }
    }

    private fun changeFilmDataInMainFragment() {
        if (isFilmSetAll) {
            mainFilmsViewModel.getFilmFromLocalSourceAllFilms()
            binding.buttonChangeFilmCategory.setImageResource(R.drawable.ic_film_category)
        } else {
            mainFilmsViewModel.getFilmFromLocalSourcePopularFilms()
            binding.buttonChangeFilmCategory.setImageResource(R.drawable.ic_popular_films)
        }.also { isFilmSetAll = !isFilmSetAll }

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
      //  adapter.removeListener()
        popularFilmsAdapter.removeListener()
        super.onDestroy()
    }

    private fun initCategoryRecyclerView() {
        val recyclerViewCategory: RecyclerView = binding.filmRecyclerViewCategory

        recyclerViewCategory.apply {
            //    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = FilmCategoryAdapter(FilmCategoryData.getParents(5))
        }
    }

    // ======== Сетим кастомные Экстеншены для SnackBar при Success: ===========
    private fun View.showSnackBarForSuccess(
        text: String, length: Int, bg: Snackbar.() -> Unit, col: Snackbar.() -> Unit
    ) {
        val sBar = Snackbar.make(this, text, length)
        sBar.bg()
        sBar.col()
        sBar.show()
    }

    private fun Snackbar.setColorSbBG() {
        setBackgroundTint(ContextCompat.getColor(context, R.color.main_fragment_tw_part_color))
    }

    private fun Snackbar.setTextSbColor(color: Int) {
        setTextColor(color)
    }
// =========================================================================
}

