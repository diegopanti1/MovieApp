package com.example.movieapplication.data.repositories.local

import androidx.lifecycle.LiveData
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {
    fun getAll() : LiveData<List<Movie>>

    suspend fun save(movies: List<Movie>): Flow<Resource<Any>>
}