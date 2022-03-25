package com.example.movieapplication.domain.interactors.movie

import com.example.movieapplication.data.repositories.local.MovieLocalRepository
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoSaveMoviesLocalUseCaseImpl @Inject constructor(private val movieLocalRepository: MovieLocalRepository) :
    DoSaveMoviesLocalUseCase {
    override suspend fun invoke(movies: List<Movie>): Flow<Resource<Any>> = movieLocalRepository.save(movies)
}