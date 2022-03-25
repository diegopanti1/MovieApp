package com.example.movieapplication.domain.interactors.movie

import com.example.movieapplication.data.repositories.remote.MovieRepository
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.models.dto.MovieDto
import com.example.movieapplication.domain.models.responses.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoGetMoviesUseCaseImpl @Inject constructor(private val repository: MovieRepository) : DoGetMoviesUseCase {
    override suspend fun invoke(): Flow<Resource<ApiResponse<MovieDto>>> = repository.getMovies()
}