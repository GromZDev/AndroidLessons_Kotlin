package com.example.kotlin_lesson_1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.databinding.FragmentFilmMainBinding
import com.example.kotlin_lesson_1.viewModel.AppState
import com.example.kotlin_lesson_1.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFilmFragment : Fragment() {

    private var _binding: FragmentFilmMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainFilmsViewModel: MainViewModel
    private val adapter = MainFilmFragmentAdapter()
    private var isFilmSetAll: Boolean = true

    private lateinit var mainView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setFilms(appState.cinemaData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.buttonChangeFilmCategory, "6666666", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reloading Data...") {
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

    companion object{
        fun newInstance() = MainFilmFragment()
    }
}