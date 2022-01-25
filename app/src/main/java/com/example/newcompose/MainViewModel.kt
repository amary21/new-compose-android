package com.example.newcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newcompose.data.IRepository
import com.example.newcompose.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    private val iRepository: IRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val movies = iRepository.getAllMovie().asLiveData()

    val favorites = iRepository.getAllFavoriteMovie().asLiveData()

    fun movie(id: Int) = iRepository.getDetailMovie(id).asLiveData()

    fun isFavorite(id: Int) = iRepository.isFavorite(id).asLiveData()

    fun setFavorite(movie: Movie, state: Boolean) = viewModelScope.launch(coroutineDispatcher){
        iRepository.setFavorite(movie, state)
    }
}