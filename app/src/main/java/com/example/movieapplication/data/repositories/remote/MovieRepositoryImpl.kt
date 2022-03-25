package com.example.movieapplication.data.repositories.remote

import com.example.movieapplication.R
import com.example.movieapplication.data.remote.MovieService
import com.example.movieapplication.di.ResourcesProvider
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.models.dto.MovieDto
import com.example.movieapplication.domain.models.responses.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(private val api: MovieService, private val resourcesProvider: ResourcesProvider) : MovieRepository, BaseRepository() {
    override suspend fun getMovies(): Flow<Resource<ApiResponse<MovieDto>>> = flow {
        emit(Resource.Loading)
        val response = getResponse(
            request = { api.getFavoriteMovies() },
            defaultErrorMessage = resourcesProvider.getString(R.string.app_name)
        )
        emit(response)
    }

}