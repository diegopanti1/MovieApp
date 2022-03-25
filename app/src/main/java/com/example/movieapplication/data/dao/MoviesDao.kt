package com.example.movieapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplication.domain.entities.Movie
@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(entities: List<Movie>)
}