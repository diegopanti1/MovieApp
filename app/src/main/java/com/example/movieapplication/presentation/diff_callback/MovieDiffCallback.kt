package com.example.movieapplication.presentation.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.movieapplication.domain.models.MovieModel

class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {

    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}