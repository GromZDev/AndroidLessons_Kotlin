package com.example.kotlin_lesson_1.view.casts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.credits.Cast
import kotlinx.android.synthetic.main.item_category_film_recyclerview.view.*

class CastsAdapter : RecyclerView.Adapter<CastsAdapter.CastViewHolder>() {

    private var castList: List<Cast> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_category_film_recyclerview, parent, false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int = castList.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    fun setActors(actors: List<Cast>) {
        this.castList = actors
        notifyDataSetChanged()
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.film_category_imageView)

        fun bind(cast: Cast) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${cast.profile_path}")
                .transform(CenterCrop())
                .into(poster)
            itemView.child_textView.text = cast.name



            itemView.setOnClickListener {
                //    onPopularFilmItemViewClickListener?.onPopularFilmItemViewClick(movie) // Вызываем слушатель нажатия
            }
        }
    }

}
