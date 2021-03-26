package com.example.kotlin_lesson_1.view


//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import com.example.kotlin_lesson_1.viewModel.MainViewModel
//import com.example.kotlin_lesson_1.R
//import com.example.kotlin_lesson_1.databinding.FragmentMainBinding
//import com.example.kotlin_lesson_1.model.FilmFeature
//import com.example.kotlin_lesson_1.viewModel.AppState
//import com.google.android.material.snackbar.Snackbar

// class FilmDetailFragment : Fragment() {

//    private lateinit var viewModel: MainViewModel
//    private var _binding: FragmentMainBinding? = null // Наш binding class этого лэйаута
//    private val binding get() = _binding!!
//    private lateinit var mainView: View
//
//    companion object {
//        fun newInstance() = FilmDetailFragment()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentMainBinding.inflate(inflater, container, false)
//        mainView = binding.root
//
//        return mainView
//    }
//
//    // В onDestroy обязательно обнуляем _binding чтобы избежать утечек!!! (В активити этого не надо)
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //   binding.twFilmName.text = "..." // Обращаемся сразу по id лэйаута (выводить в поля
//        // конкретные вьюхи уже не надо
//
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//// ViewModelProvider отвечает за создание и сохранение ViewModel. Можно получить
////ViewModel, вызвав метод get(Class modelClass) того класса, в который передаём класс,
////унаследованный от ViewModel.
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//
//// LiveData представляет собой реализацию паттерна Observable. С помощью метода observe() мы можем
//// подписаться на изменения в LiveData и получать обновлённые данные каждый раз, когда вызван
//// один из методов для передачи данных в эту LiveData. Подписка на изменения LiveData из Activity
//// выглядит так:
//        //   val observer = Observer<Any> { renderData(it) }
//
//// Метод observe() принимает два параметра. 1 LifecycleOwner — интерфейс, который реализуют
//// AppCompatActivity и Fragment из библиотеки поддержки. С его помощью можно получить объект
//// Lifecycle, а через него — callback при изменении состояния Activity или Fragment. 2 Экземпляр
//// класса, реализующего интерфейс Observer, который в методе onChanged() принимает данные из
//// LiveData. Когда подписываемся на неё, она всегда знает о состоянии Activity или Fragment.
//// Метод onChanged() вызывается, только если Activity или Fragment активны: вызван метод
//// onStart(), но не вызван onStop(). То есть LiveData всегда знает благодаря LifecycleOwner,
//// жива наша Activity или нет, чтобы не передавать данные в уничтоженную Activity.
//
//        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
//        viewModel.getFilmFromLocalSource()
//
//    }
//
//    //В renderData мы теперь принимаем объект состояния приложения и через when определяем, что нужно отображать
//    private fun renderData(appState: AppState) {
//        when (appState) {
//            is AppState.Success -> {
//                val filmData = appState.cinemaData
//                binding.loadingLayout.visibility = View.GONE
//                binding.twFilmRatingLabel.visibility = View.VISIBLE
//                binding.twFilmYearLabel.visibility = View.VISIBLE
//                setFilmData(filmData as FilmFeature)
//                Snackbar.make(mainView, "Well done my friend! ;)", Snackbar.LENGTH_LONG).show()
//            }
//            is AppState.Loading -> {
//                binding.loadingLayout.visibility = View.VISIBLE
//                binding.twFilmRatingLabel.visibility = View.GONE
//                binding.twFilmYearLabel.visibility = View.GONE
//            }
//            is AppState.Error -> {
//                binding.loadingLayout.visibility = View.GONE
//                Snackbar.make(mainView, "Sorry Bro! Some mistake...", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Reload") { viewModel.getFilmFromLocalSource() }
//                    .show()
//            }
//        }
//
//        Toast.makeText(context, "Received Data from ViewModel", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun setFilmData(filmData: FilmFeature) {
//        binding.twFilmName.text = filmData.film.filmName
//        binding.twFilmDescription.text = filmData.description
//        binding.twFilmYear.text = filmData.film.filmYear.toString()
//        binding.twFilmRating.text = filmData.film.filmRating.toString()
//        binding.iwFilmImage.setImageResource(R.drawable.film_avengers_inf)
//    }

//}