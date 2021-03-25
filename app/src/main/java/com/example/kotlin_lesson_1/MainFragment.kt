package com.example.kotlin_lesson_1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    private var buttonText: String = "Click Me!"
    private lateinit var button: Button
    private lateinit var buttonCars: Button
    private lateinit var textView: TextView
    private val word: String = "Hello Kotlin!"
    private lateinit var mainView: View
    private lateinit var carView: TextView
    private var myCarList: ArrayList<MyCar> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initMyCars()

        mainView = inflater.inflate(R.layout.fragment_main, container, false)

        textView = mainView.findViewById(R.id.textView)
        button = mainView.findViewById(R.id.button_main)
        buttonCars = mainView.findViewById(R.id.button_showCars)
        carView = mainView.findViewById(R.id.textViewCar)

        return mainView
    }

    private fun initMyCars() {
        val myCar = MyCar("Subaru", "Outback", 280)
        val myCar2 = MyCar("Kia", "Soul", 210)
        val myCar3 = MyCar("Mazda", "CX-5", 198)
        myCarList.add(myCar)
        myCarList.add(myCar2)
        myCarList.add(myCar3)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myGarage = "Show my Garage"
        buttonCars.visibility = View.INVISIBLE
        buttonCars.text = myGarage
        button.text = buttonText
        button.setOnClickListener {
            textView.text = word

            if (textView.text == word) {
                val nextWord = "Ok, let's Rock!"
                button.text = nextWord

                buttonCars.visibility = View.VISIBLE
            }
        }

        buttonCars.setOnClickListener {
            carView.text =
                "Мой виртуальный гараж насчитывает " + myCarList.size.toString() + " тачки:\n \n " + myCarList[0].brand +
                        "\n" + myCarList[1].brand + "\n " + myCarList[2].brand
        }
    }

}