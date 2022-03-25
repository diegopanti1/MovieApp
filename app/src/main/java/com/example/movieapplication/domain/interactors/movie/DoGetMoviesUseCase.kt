package com.example.movieapplication.domain.interactors.movie

import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.models.dto.MovieDto
import com.example.movieapplication.domain.models.responses.ApiResponse
import kotlinx.coroutines.flow.Flow

interface DoGetMoviesUseCase {
    suspend operator fun invoke() : Flow<Resource<ApiResponse<MovieDto>>>
}