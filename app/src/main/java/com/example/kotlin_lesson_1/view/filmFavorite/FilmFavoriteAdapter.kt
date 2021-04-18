package com.example.kotlin_lesson_1.view.filmFavorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.dto.Movie
import kotlinx.android.synthetic.main.item_fragment_favorite_films_rv.view.*

class FilmFavoriteAdapter : RecyclerView.Adapter<FilmFavoriteAdapter.FavoriteFilmViewHolder>() {

    private var favoriteList: List<Movie> = arrayListOf()

    fun setFavoriteData(data: List<Movie>) {
        this.favoriteList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteFilmViewHolder {
        return FavoriteFilmViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_fragment_favorite_films_rv,
                    parent, false
                ) as View
        )
    }

    override fun onBindViewHolder(holder: FavoriteFilmViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    inner class FavoriteFilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Movie) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                itemView.favorite_recyclerView_name.text = data.title
                itemView.favorite_item_film_rating.text = data.rating.toString()
                itemView.favorite_item_film_year.text = data.releaseDate

                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context, "Click on >>> ${data.title} favorite movie",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}
