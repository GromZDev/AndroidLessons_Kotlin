package com.example.kotlin_lesson_1.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.databinding.FragmentFilmMainBinding
import com.example.kotlin_lesson_1.model.category_RV.FilmCategoryData
import com.example.kotlin_lesson_1.model.FilmFeature
import com.example.kotlin_lesson_1.utils.showSnackBar
import com.example.kotlin_lesson_1.view.FilmDetailFragment
import com.example.kotlin_lesson_1.view.category_RV.FilmCategoryAdapter
import com.example.kotlin_lesson_1.viewModel.AppState
import com.example.kotlin_lesson_1.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_film_main.*

class MainFilmFragment : Fragment() {

    interface OnItemViewClickListener {
        fun onItemViewClick(film: FilmFeature)
    }

    private var _binding: FragmentFilmMainBinding? = null
    private val binding get() = _binding!!

    // viewModel создаем через делегирование посредством by через функцию lazy.
    // Модель будет создана только тогда, когда к ней впервые обратятся, или не будет создана,
    // если к ней так никто и не обратится. Экономим ресурсы!
    private val mainFilmsViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapter = MainFilmFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: FilmFeature) {
            val manager = activity?.supportFragmentManager
            // Если manager не null...(let)
            manager?.let {
                val bundle = Bundle()
                bundle.putParcelable(FilmDetailFragment.BUNDLE_EXTRA, film)
                manager.beginTransaction()
                    .replace(R.id.fragment_container, FilmDetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }

    }

    )
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
        binding.filmRecyclerViewVertical.adapter = adapter
        binding.buttonChangeFilmCategory.setOnClickListener {
            changeFilmDataInMainFragment()
        }

        mainFilmsViewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        mainFilmsViewModel.getFilmFromLocalSourceAllFilms()

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.filmRecyclerViewVertical.visibility = View.VISIBLE
                binding.twPartName.visibility = View.VISIBLE
                initCategoryRecyclerView()
                adapter.setFilms(appState.cinemaData)

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
        adapter.removeListener()
        super.onDestroy()
    }

    private fun initCategoryRecyclerView() {
        val recyclerViewCategory: RecyclerView = film_recyclerView_category

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

