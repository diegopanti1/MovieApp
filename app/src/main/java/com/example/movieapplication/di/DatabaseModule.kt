package com.example.movieapplication.di

import android.content.Context
import com.example.movieapplication.BuildConfig
import com.example.movieapplication.data.dao.MovieDatabase
import com.example.movieapplication.data.dao.MoviesDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class )
@Module
object DatabaseModule {
    //region Database
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context): MovieDatabase = MovieDatabase.getInstance(appContext)
    //endregion

    //region Dao
    @Provides
    @Singleton
    fun provideMovieDAo(database : MovieDatabase): MoviesDao {
        return database.getMovieDao()
    }
    //endregion

    //region Firestore
    @Provides
    @Singleton
    fun provideFirestoreDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance()
    //endregion

    //region Storage
    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage = FirebaseStorage.getInstance(BuildConfig.BUCKET_STORAGE)
    //endregion
}