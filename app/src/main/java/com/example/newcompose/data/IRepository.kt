package com.example.newcompose.data

import com.example.newcompose.data.model.Detail
import com.example.newcompose.data.model.Movie
import com.example.newcompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getAllFavoriteMovie(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Int) : Flow<Resource<Detail>>
    suspend fun setFavorite(movie: Movie, state: Boolean)
    fun isFavorite(id: Int) : Flow<Int>
}