package com.example.kotlin_lesson_1.view.category_RV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.FilmFeature
import kotlinx.android.synthetic.main.item_category_film_recyclerview.view.*

class SpecificFilmAdapter (private val specificFilm: List<FilmFeature>) :
        RecyclerView.Adapter<SpecificFilmAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_film_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFilm = specificFilm[position]
        holder.imageView.setImageResource(currentFilm.film.filmImage)
        holder.textView.text = currentFilm.film.filmName
    }

    override fun getItemCount(): Int {
        return specificFilm.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textView: TextView = itemView.child_textView
        var imageView: ImageView = itemView.film_category_imageView
    }

}