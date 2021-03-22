package com.example.kotlin_lesson_1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class MainFragment : Fragment() {

    private var buttonText: String = "Click Me!"
    private lateinit var button: Button
    private lateinit var textView: TextView
    private val word: String = "Hello Kotlin!"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById(R.id.textView)

        button = view.findViewById(R.id.button_main)
        button.setText(buttonText)
        button.setOnClickListener(View.OnClickListener {
            textView.setText(word)

            if (textView.text.equals(word)) {
                val nextWord = "Ok, let's Rock!"
                button.setText(nextWord)
            }
        })

    }

}