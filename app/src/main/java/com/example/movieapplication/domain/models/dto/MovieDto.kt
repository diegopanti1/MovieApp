package com.example.movieapplication.domain.models.dto

import com.example.movieapplication.BuildConfig
import com.example.movieapplication.domain.entities.Movie
import com.example.movieapplication.domain.models.MovieModel

data class MovieDto(
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
) {
    fun toModel() : MovieModel {
        return MovieModel(id,overview,BuildConfig.BASE_IMAGE_URL + poster_path,title)
    }

    fun toEntity() : Movie {
        return Movie(_id = id.toString(), title = title, overview = overview, poster_path = poster_path )
    }
}
