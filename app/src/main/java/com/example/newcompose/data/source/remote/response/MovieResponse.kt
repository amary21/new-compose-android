package com.example.newcompose.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val results: List<ResultResponse>
)
