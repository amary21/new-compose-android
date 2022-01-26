package com.example.newcompose.data.source.local.room

import androidx.room.*
import com.example.newcompose.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie where is_favorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT EXISTS (SELECT * FROM movie WHERE id=:id AND is_favorite=1)")
    fun isFavorite(id: Int): Flow<Int>

    @Query("SELECT COUNT(is_favorite) FROM movie WHERE is_favorite=1")
    fun countFavorite(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateFavoriteMovie(movie: MovieEntity)
}