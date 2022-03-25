package com.example.movieapplication.domain.interactors.movie

import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface DoSaveMoviesLocalUseCase {
    suspend operator fun invoke(movies: List<Movie>) : Flow<Resource<Any>>
}