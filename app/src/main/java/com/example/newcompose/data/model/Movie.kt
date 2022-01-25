package com.example.newcompose.data.model

import com.example.newcompose.data.source.local.entity.MovieEntity
import java.io.Serializable

data class Movie(
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val isFavorite: Boolean
) : Serializable


fun Movie.mapToEntity() : MovieEntity = MovieEntity(
    id, voteAverage, title, posterPath, backdropPath, releaseDate
)
