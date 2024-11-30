package com.mkdevelopment.tmdbapp.model


import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("results")
    val results: List<MovieItem?>?
)