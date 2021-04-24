package com.example.kotlin_lesson_MyMovieApp.view.category_RV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_MyMovieApp.R
import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature
import com.example.kotlin_lesson_MyMovieApp.view.main.MainFilmFragment
import kotlinx.android.synthetic.main.item_category_film_recyclerview.view.*

class SpecificFilmAdapter(
    private val specificFilm: List<FilmFeature>, private var onItemViewClickListener:
    MainFilmFragment.OnItemViewClickListener?
) :
    RecyclerView.Adapter<SpecificFilmAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_film_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFilm = specificFilm[position]
        holder.imageView.setImageResource(currentFilm.film.filmImage)
        holder.textView.text = currentFilm.film.filmName

        holder.bind(specificFilm[position])
    }

    override fun getItemCount(): Int = specificFilm.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.child_textView
        var imageView: ImageView = itemView.film_category_imageView

        fun bind(films: FilmFeature) {
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(films) // Вызываем слушатель нажатия
            }
        }

    }

    fun removeListener() {
        onItemViewClickListener = null
    }

}