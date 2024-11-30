package com.mkdevelopment.tmdbapp.network

import com.mkdevelopment.tmdbapp.model.MovieDetail
import com.mkdevelopment.tmdbapp.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("popular")
    suspend fun getMovieList(@Header("Authorization") token:String):Response<MovieModel>


    @GET("{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId:String,@Header("Authorization") token:String):Response<MovieDetail>
}