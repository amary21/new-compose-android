package com.example.newcompose.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newcompose.data.model.Movie
import com.example.newcompose.utils.Constant.COVER_IMAGE
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ItemGrid(data: Movie) {
    Card(
        modifier = Modifier
            .clickable {
                Log.e("ItemGrid", data.toString())
            }
            .padding(10.dp)
            .fillMaxSize(),
        elevation =  5.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                modifier = Modifier.
                    height(200.dp),
                imageModel = COVER_IMAGE + data.posterPath)
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = data.title,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                fontSize =  15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = data.voteAverage.toString(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp),
                fontSize =  13.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )


        }
    }
}
