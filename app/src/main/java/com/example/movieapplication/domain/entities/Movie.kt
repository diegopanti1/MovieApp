package com.example.movieapplication.domain.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movieapplication.BuildConfig
import com.example.movieapplication.domain.models.MovieModel

@Entity(
    tableName = "movies",
    indices = [Index(value = ["_id"], unique = true)]
)
data class Movie (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var _id: String,
    val title: String,
    val overview: String,
    val poster_path: String
) {
    fun toModel(): MovieModel {
        return MovieModel(id!!,overview, BuildConfig.BASE_IMAGE_URL + poster_path, title)
    }
}