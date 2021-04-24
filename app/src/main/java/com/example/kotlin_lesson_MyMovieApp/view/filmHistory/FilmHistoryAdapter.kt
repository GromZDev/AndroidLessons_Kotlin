package com.example.kotlin_lesson_MyMovieApp.view.filmHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_MyMovieApp.R
import com.example.kotlin_lesson_MyMovieApp.model.dto.Movie
import kotlinx.android.synthetic.main.item_fragment_film_history_recyclerview.view.*

class FilmHistoryAdapter : RecyclerView.Adapter<FilmHistoryAdapter.RecyclerHistoryViewHolder>() {

    private var historyList: List<Movie> = arrayListOf()

    fun setHistoryData(data: List<Movie>) {
        this.historyList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerHistoryViewHolder {
        return RecyclerHistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_fragment_film_history_recyclerview,
                    parent, false
                ) as View
        )
    }

    override fun onBindViewHolder(
        holder: FilmHistoryAdapter.RecyclerHistoryViewHolder,
        position: Int
    ) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class RecyclerHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Movie) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                itemView.history_recyclerView_name.text = data.title
                itemView.history_item_film_rating.text = data.rating.toString()
                itemView.history_item_film_year.text = data.releaseDate

                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context, "Click on >>> ${data.title}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}