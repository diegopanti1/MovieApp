package com.example.movieapplication.presentation.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.R
import com.example.movieapplication.di.ResourcesProvider
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.entities.Movie
import com.example.movieapplication.domain.interactors.movie.DoGetMoviesLocalUseCase
import com.example.movieapplication.domain.interactors.movie.DoGetMoviesUseCase
import com.example.movieapplication.domain.interactors.movie.DoSaveMoviesLocalUseCase
import com.example.movieapplication.domain.models.dto.MovieDto
import com.example.movieapplication.domain.models.responses.ApiResponse
import com.example.movieapplication.platform.NetworkHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val doGetMoviesUseCase: DoGetMoviesUseCase,
    private val doGetMoviesLocalUseCase: DoGetMoviesLocalUseCase,
    private val doSaveMoviesLocalUseCase: DoSaveMoviesLocalUseCase,
    private val networkHandler: NetworkHandler,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    private val _movies = MutableLiveData<Resource<ApiResponse<MovieDto>>>()
    val movies: LiveData<Resource<ApiResponse<MovieDto>>> get() = _movies

    var localMovies: LiveData<List<Movie>>? = null

    fun observeMovies() {
        localMovies = doGetMoviesLocalUseCase.invoke()
    }

    fun getMoviesFromCloud() {
        if (networkHandler.isConnected) {
            _movies.postValue(Resource.Loading)
            viewModelScope.launch {
                doGetMoviesUseCase().collect { result ->
                    when (result) {
                        is Resource.Success -> _movies.postValue(result)
                        is Resource.Failure -> _movies.postValue(result)
                        else -> _movies.postValue(Resource.Loading)
                    }
                }
            }
        } else {
            _movies.postValue(
                Resource.Failure(
                    true,
                    404,
                    resourcesProvider.getString(R.string.error_network)
                )
            )
        }
    }

    suspend fun saveLocalMovies(movies: List<Movie>){
        doSaveMoviesLocalUseCase.invoke(movies).collect()
    }
}