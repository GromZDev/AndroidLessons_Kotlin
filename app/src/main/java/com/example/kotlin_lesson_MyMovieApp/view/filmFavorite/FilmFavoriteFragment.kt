package com.example.kotlin_lesson_MyMovieApp.view.filmFavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lesson_MyMovieApp.databinding.FragmentFilmFavoriteBinding
import com.example.kotlin_lesson_MyMovieApp.utils.showSnackBar
import com.example.kotlin_lesson_MyMovieApp.viewModel.appStates.AppStateFavorite
import com.example.kotlin_lesson_MyMovieApp.viewModel.favoriteFilmViewModel.FavoriteFilmViewModel
import kotlinx.android.synthetic.main.fragment_film_favorite.*

class FilmFavoriteFragment : Fragment() {
    private var _binding: FragmentFilmFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteFilmViewModel by lazy {
        ViewModelProvider(this).get(FavoriteFilmViewModel::class.java)
    }

    private val adapter: FilmFavoriteAdapter by lazy { FilmFavoriteAdapter() }

    companion object {
        fun newInstance(): FilmFavoriteFragment {
            return FilmFavoriteFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteFragmentRecyclerview.adapter = adapter
        favoriteViewModel.favoriteFilmLiveData.observe(
            viewLifecycleOwner,
            Observer { setFavoriteFilmsData(it) })
        favoriteViewModel.getAllFavoriteFilms()
    }

    private fun setFavoriteFilmsData(appStateFavorite: AppStateFavorite) {
        when (appStateFavorite) {
            is AppStateFavorite.Success -> {
                binding.favoriteFragmentRecyclerview.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                adapter.setFavoriteData(appStateFavorite.favoriteMovieData)
            }
            is AppStateFavorite.Loading -> {
                binding.favoriteFragmentRecyclerview.visibility = View.GONE
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppStateFavorite.Error -> {
                binding.favoriteFragmentRecyclerview.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE

                binding.favoriteFragmentRecyclerview.showSnackBar(
                    "Ахтунг!!!",
                    "<<<<<<<<<<<<<<<<<<<<<<<<",
                    {
                        favoriteViewModel.getAllFavoriteFilms()
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}