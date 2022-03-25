package com.example.movieapplication.presentation.view_holders

import android.view.View
import com.example.movieapplication.databinding.ItemMovieBinding
import com.example.movieapplication.domain.models.MovieModel

class MoviesRecyclerViewHolder (private val binding: ItemMovieBinding) : BaseViewHolder<MovieModel>(binding) {

    var itemClickListener: ((view: View, item: MovieModel, position: Int)->Unit)? = null

    override fun bind(item: MovieModel) {
        binding.movie = item
        binding.root.setOnClickListener {
            itemClickListener?.invoke(it, item, absoluteAdapterPosition)
        }
    }
}