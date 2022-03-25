package com.example.movieapplication.data.remote

import com.example.movieapplication.domain.models.dto.MovieDto
import com.example.movieapplication.domain.models.responses.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {
    @GET("discover/movie")
    suspend fun getFavoriteMovies() : Response<ApiResponse<MovieDto>>
}