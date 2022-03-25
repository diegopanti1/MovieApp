package com.example.movieapplication.domain

import com.example.movieapplication.data.repositories.local.MovieLocalRepository
import com.example.movieapplication.data.repositories.local.MovieLocalRepositoryImpl
import com.example.movieapplication.data.repositories.remote.MovieRepository
import com.example.movieapplication.data.repositories.remote.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    //region Remote
    @Binds
    fun bindMovieRepository(movieRepository: MovieRepositoryImpl) : MovieRepository
    //endregion
    //region Local
    @Binds
    fun bindLocalMovieRepository(movieRepository: MovieLocalRepositoryImpl) : MovieLocalRepository
    //endregion
}