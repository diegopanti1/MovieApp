package com.example.movieapplication.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapplication.domain.entities.Movie

@Database(
    entities = [
        Movie::class
    ], version = 1, exportSchema = false
)
abstract class MovieDatabase  : RoomDatabase(){
    abstract fun getMovieDao(): MoviesDao

    companion object {
        private var INSTANCE: MovieDatabase? = null
        private const val databaseName= "movieapp.db"

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, MovieDatabase::class.java, databaseName)
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}
