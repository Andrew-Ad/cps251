package com.example.moviesearch.api

import androidx.contentpager.content.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue
import retrofit2.http.GET
import retrofit2.http.Query


object RetrofitClient {
    private const val BASE_URL = "https://www.omdbapi.com/?s=batman&apikey=7010df9e"

    val MovieSearchApiService: MovieSearchApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieSearchApiService::class.java)
    }
}

data class Movie(
    val year: Int,
    val rated: String,
    val director: String,
    val actors: String,
    val rottentomato: String,
    val imdbrating: String,
    val imdbid: String,
    val title: String,
    val poster: String,
    val plot: String,
    val boxoffice: String
)
