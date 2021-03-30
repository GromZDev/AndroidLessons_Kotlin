package com.example.kotlin_lesson_1.view.category_RV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.model.category_RV.FilmSpecificListCategory
import kotlinx.android.synthetic.main.item_category_recyclerview.view.*

class FilmCategoryAdapter (private val specificListCategory: List<FilmSpecificListCategory>) :
        RecyclerView.Adapter<FilmCategoryAdapter.ViewHolder>(){

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val v = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_category_recyclerview, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var films = specificListCategory[position]
        holder.textView.text = films.title

        var childLayoutManager = LinearLayoutManager(holder.recyclerView.context,
            RecyclerView.HORIZONTAL, false)

        childLayoutManager.initialPrefetchItemCount = 2

        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = SpecificFilmAdapter(films.children)
            setRecycledViewPool(viewPool)
        }

    }

    override fun getItemCount(): Int {
        return specificListCategory.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val recyclerView: RecyclerView = itemView.rv_child
        val textView: TextView = itemView.textView_film_child
    }

}
