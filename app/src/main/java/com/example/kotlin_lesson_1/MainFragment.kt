package com.example.kotlin_lesson_1


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainFragment : Fragment() {

    private lateinit var mainView: View
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainView = inflater.inflate(R.layout.fragment_main, container, false)

        return mainView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

// ViewModelProvider отвечает за создание и сохранение ViewModel. Можно получить
//ViewModel, вызвав метод get(Class modelClass) того класса, в который передаём класс,
//унаследованный от ViewModel.
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

// LiveData представляет собой реализацию паттерна Observable. С помощью метода observe() мы можем
// подписаться на изменения в LiveData и получать обновлённые данные каждый раз, когда вызван
// один из методов для передачи данных в эту LiveData. Подписка на изменения LiveData из Activity
// выглядит так:
        val observer = Observer<Any> {renderData(it)}

// Метод observe() принимает два параметра. 1 LifecycleOwner — интерфейс, который реализуют
// AppCompatActivity и Fragment из библиотеки поддержки. С его помощью можно получить объект
// Lifecycle, а через него — callback при изменении состояния Activity или Fragment. 2 Экземпляр
// класса, реализующего интерфейс Observer, который в методе onChanged() принимает данные из
// LiveData. Когда подписываемся на неё, она всегда знает о состоянии Activity или Fragment.
// Метод onChanged() вызывается, только если Activity или Fragment активны: вызван метод
// onStart(), но не вызван onStop(). То есть LiveData всегда знает благодаря LifecycleOwner,
// жива наша Activity или нет, чтобы не передавать данные в уничтоженную Activity.

        viewModel.getLiveData().observe(viewLifecycleOwner, observer)

    }

    private fun renderData(data: Any) {
            Toast.makeText(context, "data", Toast.LENGTH_SHORT).show()
    }

}