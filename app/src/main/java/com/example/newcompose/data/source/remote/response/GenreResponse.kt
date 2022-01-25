package com.example.newcompose.data.source.remote.response

import com.example.newcompose.data.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("name") val name: String
)

fun GenreResponse.mapToModel() : Genre = Genre(name)

fun List<GenreResponse>.mapToModel() : List<Genre> = map { it.mapToModel() }