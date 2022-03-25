package com.example.movieapplication.presentation

import com.example.movieapplication.domain.interactors.movie.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PresentationModule {
    //region Movie Remote
    @Binds
    fun bindDoGetMoviesUseCase(doGetMoviesUseCase: DoGetMoviesUseCaseImpl): DoGetMoviesUseCase
    //endregion

    //region Movie Local
    @Binds
    fun bindDoGetMoviesLocalUseCase(doGetMoviesUseCase: DoGetMoviesLocalUseCaseImpl): DoGetMoviesLocalUseCase

    @Binds
    fun bindDoSaveMoviesLocalUseCase(doSaveMoviesUseCase: DoSaveMoviesLocalUseCaseImpl): DoSaveMoviesLocalUseCase
    //endregion

}