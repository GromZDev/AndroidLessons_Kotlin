package com.example.kotlin_lesson_1.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.FilmFeature

class MainFilmFragmentAdapter : RecyclerView.Adapter<MainFilmFragmentAdapter.FilmViewHolder>() {

    private var filmsData: List<FilmFeature> = mutableListOf()

    fun setFilms(data: List<FilmFeature>) {
        filmsData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFilmFragmentAdapter.FilmViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_film_recycler_view_vertical, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainFilmFragmentAdapter.FilmViewHolder, position: Int) {
        holder.bind(filmsData[position])
    }

    override fun getItemCount(): Int {
        return filmsData.size
    }

// Внутренний класс ViewHolder
    inner class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(films: FilmFeature) {
            itemView.findViewById<TextView>(R.id.item_film_name).text = films.film.filmName
            itemView.findViewById<ImageView>(R.id.item_film_image).setImageResource(films.film.filmImage)

            itemView.findViewById<ImageView>(R.id.item_rating_image)
            itemView.findViewById<TextView>(R.id.item_film_rating).text = films.film.filmRating.toString()

            itemView.findViewById<ImageView>(R.id.item_time_length_image)
            itemView.findViewById<TextView>(R.id.item_film_time_length).text = "136"

            itemView.findViewById<ImageView>(R.id.item_film_year_image)
            itemView.findViewById<TextView>(R.id.item_film_year).text = films.film.filmYear.toString()

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, films.film.filmName, Toast.LENGTH_SHORT).show()
            }
        }

    }

}