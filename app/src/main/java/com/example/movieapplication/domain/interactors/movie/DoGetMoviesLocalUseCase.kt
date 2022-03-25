package com.example.movieapplication.domain.interactors.movie

import androidx.lifecycle.LiveData
import com.example.movieapplication.domain.entities.Movie

interface DoGetMoviesLocalUseCase {
    operator fun invoke() : LiveData<List<Movie>>
}