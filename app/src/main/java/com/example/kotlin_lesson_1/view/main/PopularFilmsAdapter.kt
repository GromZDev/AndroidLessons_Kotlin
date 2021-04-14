package com.example.kotlin_lesson_1.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.dto.Movie

class PopularFilmsAdapter(
    private var movies: MutableList<Movie>
) : RecyclerView.Adapter<PopularFilmsAdapter.PopularFilms>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFilms {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_film_recycler_view_vertical, parent, false)
        return PopularFilms(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: PopularFilms, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    inner class PopularFilms(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_film_image)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            itemView.findViewById<TextView>(R.id.item_film_name)
            itemView.findViewById<TextView>(R.id.item_film_name).text = movie.title

            itemView.findViewById<ImageView>(R.id.item_rating_image)
            itemView.findViewById<TextView>(R.id.item_film_rating).text = movie.rating.toString()

            itemView.findViewById<ImageView>(R.id.item_film_year_image)
            itemView.findViewById<TextView>(R.id.item_film_year).text = movie.releaseDate
        }
    }
}