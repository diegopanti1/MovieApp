package com.example.movieapplication.data.repositories.remote

import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.models.dto.MovieDto
import com.example.movieapplication.domain.models.responses.ApiResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<Resource<ApiResponse<MovieDto>>>
}