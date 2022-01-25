package com.example.newcompose.data.source.local

import com.example.newcompose.data.source.local.entity.MovieEntity
import com.example.newcompose.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovies() = movieDao.getAllMovies()

    fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    fun isFavorite(id: Int) = movieDao.isFavorite(id)

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}