package com.example.newcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newcompose.data.IRepository
import com.example.newcompose.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel() {
    private val iRepository = object : KoinComponent {val iRepository: IRepository by inject()}.iRepository
    private val coroutineDispatcher = Dispatchers.IO

    val movies = iRepository.getAllMovie().asLiveData()

    val favorites = iRepository.getAllFavoriteMovie().asLiveData()

    val countFavorite = iRepository.countFavorite().asLiveData()

    fun movie(id: Int) = iRepository.getDetailMovie(id).asLiveData()

    fun isFavorite(id: Int) = iRepository.isFavorite(id).asLiveData()

    fun setFavorite(movie: Movie, state: Boolean) = viewModelScope.launch(coroutineDispatcher){
        iRepository.setFavorite(movie, state)
    }
}