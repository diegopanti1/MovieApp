package com.example.movieapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.databinding.ItemMovieBinding
import com.example.movieapplication.domain.models.MovieModel
import com.example.movieapplication.presentation.diff_callback.MovieDiffCallback
import com.example.movieapplication.presentation.view_holders.MoviesRecyclerViewHolder

class MovieRecyclerViewAdapter : RecyclerView.Adapter<MoviesRecyclerViewHolder>() {
    private val diffCallback = MovieDiffCallback()

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<MovieModel>) {
        differ.submitList(list)
    }

    private var itemClickListener: ((view: View, item: MovieModel, position: Int)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesRecyclerViewHolder {
        return MoviesRecyclerViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesRecyclerViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}