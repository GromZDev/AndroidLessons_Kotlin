package com.example.kotlin_lesson_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    private var buttonText: String = "Click Me!"
    private lateinit var button: Button
    private lateinit var buttonCars: Button
    private lateinit var textView: TextView
    private val word: String = "Hello Kotlin!"
    private lateinit var mainView: View
    private lateinit var carView: TextView
    private lateinit var myCar: MyCar
    private lateinit var myCar2: MyCar
    private lateinit var myCar3: MyCar
    private var myCarList: ArrayList<MyCar> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myCar = MyCar("Subaru", "Impreza", 280)
        myCar2 = MyCar("Kia", "Soul", 210)
        myCar3 = MyCar("Mazda", "CX-5", 198)

        mainView = inflater.inflate(R.layout.fragment_main, container, false)

        textView = mainView.findViewById(R.id.textView)
        button = mainView.findViewById(R.id.button_main)
        buttonCars = mainView.findViewById(R.id.button_showCars)
        carView = mainView.findViewById(R.id.textViewCar)

        return mainView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myGarage: String = "Show my Garage"
        buttonCars.setVisibility(View.INVISIBLE)
        buttonCars.setText(myGarage)
        button.setText(buttonText)
        button.setOnClickListener(View.OnClickListener {
            textView.setText(word)

            if (textView.text.equals(word)) {
                val nextWord = "Ok, let's Rock!"
                button.setText(nextWord)
                //  carView.setText(myCar.toString())
                buttonCars.setVisibility(View.VISIBLE)
            }
        })


        myCarList.add(myCar)
        myCarList.add(myCar2)

        buttonCars.setOnClickListener(View.OnClickListener {
            carView.setText(myCarList.get(0).brand.toString())
        })

     //   carView.setText(myCarList.size.toString() + "\n " + myCarList.get(0).toString())


    }


}