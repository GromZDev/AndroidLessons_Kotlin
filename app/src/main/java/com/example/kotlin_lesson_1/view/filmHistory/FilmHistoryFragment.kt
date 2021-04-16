package com.example.kotlin_lesson_1.view.filmHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lesson_1.databinding.FragmentFilmHistoryBinding
import com.example.kotlin_lesson_1.utils.showSnackBar
import com.example.kotlin_lesson_1.viewModel.AppStateHistory
import com.example.kotlin_lesson_1.viewModel.FilmHistoryViewModel
import kotlinx.android.synthetic.main.fragment_film_history.*

class FilmHistoryFragment : Fragment() {

    private var _binding: FragmentFilmHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: FilmHistoryViewModel by lazy {
        ViewModelProvider(this).get(FilmHistoryViewModel::class.java)
    }

    private val adapter: FilmHistoryAdapter by lazy { FilmHistoryAdapter() }

    companion object {
        fun newInstance(): FilmHistoryFragment {
            return FilmHistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyFragmentRecyclerview.adapter = adapter
        historyViewModel.historyLiveData.observe(
            viewLifecycleOwner,
            Observer { setHistoryData(it) })
        historyViewModel.getAllFilmHistory()
    }

    private fun setHistoryData(appStateHistory: AppStateHistory) {
        when (appStateHistory) {
            is AppStateHistory.Success -> {
                binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                adapter.setHistoryData(appStateHistory.historyMovieData)
            }
            is AppStateHistory.Loading -> {
                binding.historyFragmentRecyclerview.visibility = View.GONE
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppStateHistory.Error -> {
                binding.historyFragmentRecyclerview.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE

                binding.historyFragmentRecyclerview.showSnackBar(
                    "Ахтунг!!!",
                    "<<<<<<<<<<<<<<<<<<<<<<<<",
                    {
                        historyViewModel.getAllFilmHistory()
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}