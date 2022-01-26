package com.example.newcompose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.newcompose.data.model.Detail
import com.example.newcompose.data.model.Movie
import com.example.newcompose.utils.Resource

@ExperimentalFoundationApi
@Composable
fun FavoriteScreen(viewModelLiveData: LiveData<Resource<List<Movie>>>){
    val movies by viewModelLiveData.observeAsState(initial = Resource.Error("data empty"))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when(movies){
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Success -> {
                val moviesData = movies.data as List<Movie>
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    modifier = Modifier.padding(10.dp)
                ){
                    items(moviesData) { data ->
                        ItemGrid(data)
                    }
                }
            }
            is Resource.Error -> {
                Text(text = "Data Not Found")
            }
        }
    }
}