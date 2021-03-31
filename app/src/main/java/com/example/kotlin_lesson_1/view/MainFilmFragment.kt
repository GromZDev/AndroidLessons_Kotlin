package com.example.kotlin_lesson_1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.databinding.FragmentFilmMainBinding
import com.example.kotlin_lesson_1.model.category_RV.FilmCategoryData
import com.example.kotlin_lesson_1.model.FilmFeature
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
    private lateinit var mainFilmsViewModel: MainViewModel
// Теперь в MainFilmFragment’е создаём интерфейс и передаём его в адаптер. Интерфейс создаемчерез
// ключевое слово object. В самом методе onItemViewClick обращаемся к менеджеру фрагментов
// через активити и создаём бандл. Добавляем в бандл получаемый парс-класс и открываем новый фрагмент:

    private val adapter = MainFilmFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: FilmFeature) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
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
        mainFilmsViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainFilmsViewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        mainFilmsViewModel.getFilmFromLocalSourceAllFilms()

   // ================================
      //  initCategoryRecyclerView()
        //=================================


    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                initCategoryRecyclerView()

                adapter.setFilms(appState.cinemaData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.buttonChangeFilmCategory,
                    getString(R.string.error),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(getString(R.string.reloading)) {
                        mainFilmsViewModel.getFilmFromLocalSourceAllFilms()
                    }.show()
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
        }
        isFilmSetAll = !isFilmSetAll
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
}