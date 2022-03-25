package com.example.movieapplication.domain.interactors.movie

import androidx.lifecycle.LiveData
import com.example.movieapplication.data.repositories.local.MovieLocalRepository
import com.example.movieapplication.domain.entities.Movie
import javax.inject.Inject

class DoGetMoviesLocalUseCaseImpl @Inject constructor(
    private val movieLocalRepository: MovieLocalRepository
) : DoGetMoviesLocalUseCase {
    override fun invoke(): LiveData<List<Movie>> = movieLocalRepository.getAll()
}