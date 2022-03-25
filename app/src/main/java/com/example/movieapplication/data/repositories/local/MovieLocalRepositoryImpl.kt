package com.example.movieapplication.data.repositories.local

import androidx.lifecycle.LiveData
import com.example.movieapplication.data.dao.MoviesDao
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.entities.Movie
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(private val moviesDao: MoviesDao) : MovieLocalRepository {
    override fun getAll(): LiveData<List<Movie>>  =
        moviesDao.getAllMovies()

    override suspend fun save(movies: List<Movie>) = flow {
        emit(Resource.Loading)
        val response = moviesDao.saveMovies(movies)
        emit(Resource.Success(response))
    }
}