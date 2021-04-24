package com.example.kotlin_lesson_MyMovieApp.view.category_RV

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lesson_MyMovieApp.R
import com.example.kotlin_lesson_MyMovieApp.model.FilmFeature
import com.example.kotlin_lesson_MyMovieApp.model.category_RV.FilmSpecificListCategory
import com.example.kotlin_lesson_MyMovieApp.view.filmDetails.FilmDetailFragment
import com.example.kotlin_lesson_MyMovieApp.view.main.MainFilmFragment
import kotlinx.android.synthetic.main.item_category_recyclerview.view.*


class FilmCategoryAdapter(private val specificListCategory: List<FilmSpecificListCategory>) :
    RecyclerView.Adapter<FilmCategoryAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private lateinit var fragmentManager: FragmentManager // Нужен для получения контекста ниже для
    // передачи в транзакцию

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_recyclerview, parent, false)

        fragmentManager = (v.context as FragmentActivity).supportFragmentManager // получаю менеджер

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val films = specificListCategory[position]
        holder.textView.text = films.title

        val childLayoutManager = LinearLayoutManager(
            holder.recyclerView.context,
            RecyclerView.HORIZONTAL, false
        )

        childLayoutManager.initialPrefetchItemCount = 2

        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = SpecificFilmAdapter(films.children, object :
                MainFilmFragment.OnItemViewClickListener {
                override fun onItemViewClick(film: FilmFeature) {
                    //================= сетим переход =================
                    val bundle = Bundle()
                    bundle.putParcelable(FilmDetailFragment.BUNDLE_EXTRA, film)
                    fragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            FilmDetailFragment.newInstance(bundle)
                        )
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                    //=============================
                }
            })
            setRecycledViewPool(viewPool)
        }

    }

    override fun getItemCount(): Int = specificListCategory.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.rv_child
        val textView: TextView = itemView.textView_film_child
    }

}
