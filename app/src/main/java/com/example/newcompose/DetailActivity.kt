package com.example.newcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.newcompose.data.model.Detail
import com.example.newcompose.ui.theme.MainTheme
import com.example.newcompose.utils.Constant
import com.example.newcompose.utils.Resource
import com.skydoves.landscapist.glide.GlideImage
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme() {
                Scaffold(
                    topBar = {
                        TopBar(
                            title = intent.getStringExtra("title") ?: "",
                            onBackClick = { finish() }
                        )
                    }
                ) {
                    InitContent(viewModel.movie(intent.getIntExtra("id_movie", 0)))
                }
            }
        }
    }
}

@Composable
fun TopBar(title: String, onBackClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation =  8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
        )
    }
}

@Composable
fun InitContent(movieLiveData: LiveData<Resource<Detail>>) {
    val movie by movieLiveData.observeAsState(initial = Resource.Error("data empty"))
    when(movie){
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            movie.data?.backdropPath?.let { BannerView(it) }
        }
        is Resource.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Data Not Found")
            }
        }
    }
}

@Composable
fun BannerView(backdropPath: String) {
    Column {
        Card(
            modifier = Modifier.padding(horizontal = 10.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            GlideImage(
                modifier = Modifier.height(200.dp),
                contentScale = ContentScale.Crop,
                imageModel = Constant.COVER_IMAGE + backdropPath)
        }
    }
}