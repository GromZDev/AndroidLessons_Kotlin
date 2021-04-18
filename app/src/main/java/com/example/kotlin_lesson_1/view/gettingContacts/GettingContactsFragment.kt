package com.example.kotlin_lesson_1.view.gettingContacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lesson_1.databinding.FragmentFilmHistoryBinding
import com.example.kotlin_lesson_1.databinding.FragmentReceiveContactsFromContentProviderBinding
import com.example.kotlin_lesson_1.view.filmHistory.FilmHistoryAdapter
import com.example.kotlin_lesson_1.view.filmHistory.FilmHistoryFragment
import com.example.kotlin_lesson_1.viewModel.filmHistoryViewModel.FilmHistoryViewModel
import kotlinx.android.synthetic.main.fragment_film_history.*

class GettingContactsFragment: Fragment() {

    private var _binding: FragmentReceiveContactsFromContentProviderBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): GettingContactsFragment {
            return GettingContactsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiveContactsFromContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}