package com.example.newcompose.data

import android.content.Context
import com.example.newcompose.data.model.Detail
import com.example.newcompose.data.model.Movie
import com.example.newcompose.data.model.mapToEntity
import com.example.newcompose.data.source.local.LocalDataSource
import com.example.newcompose.data.source.local.entity.mapToModel
import com.example.newcompose.data.source.remote.RemoteDataSource
import com.example.newcompose.data.source.remote.response.ResultResponse
import com.example.newcompose.data.source.remote.response.mapToEntity
import com.example.newcompose.data.source.remote.response.mapToModel
import com.example.newcompose.utils.ApiResponse
import com.example.newcompose.utils.ConnectionCheck
import com.example.newcompose.utils.NetworkBoundResource
import com.example.newcompose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val context: Context ) : IRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultResponse>>() {
            override fun loadFromDB() =
                localDataSource.getAllMovies().map {
                    it.mapToModel()
                }

            override fun shouldFetch(data: List<Movie>?) = data?.isEmpty() == true

            override suspend fun createCall() =
                remoteDataSource.getAllMovies()


            override suspend fun saveCallResult(data: List<ResultResponse>) {
                localDataSource.insertMovie(data.mapToEntity())
            }

        }.asFlow()

    override fun getAllFavoriteMovie(): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = localDataSource.getFavoriteMovie().first()
                if (result.isNotEmpty()){
                    emit(Resource.Success(result.mapToModel()))
                } else {
                    emit(Resource.Error<List<Movie>>("data empty"))
                }
            } catch (e: Exception){
                emit(Resource.Error<List<Movie>>(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getDetailMovie(id: Int): Flow<Resource<Detail>> {
        return flow {
            emit(Resource.Loading())
            when(val result = remoteDataSource.getDetailMovie(id).first()){
                is ApiResponse.Success -> emit(Resource.Success(result.data.mapToModel()))
                is ApiResponse.Error -> emit(Resource.Error<Detail>(result.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Error<Detail>("data empty"))
            }
        }
    }

    override suspend fun setFavorite(movie: Movie, state: Boolean) =
        localDataSource.setFavoriteMovie(movie.mapToEntity(), state)

    override fun isFavorite(id: Int): Flow<Int> {
        return localDataSource.isFavorite(id)
    }

    override fun countFavorite(): Flow<Int> {
        return localDataSource.countFavorite()
    }


}