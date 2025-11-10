package com.example.moviesearch.api

interface OmdbApiService {
        @GET("https://www.omdbapi.com/?s=batman&apikey=7010df9e")
        suspend fun getCurrentMovie(
            @Query("year") year: Int,
            @Query("rated") rated: String,
            @Query("director") director: String,
            @Query("actors") actors: String,
            @Query("rottentomato") rottentomato: String,
            @Query("imdbrating") imdbrating: String,
            @Query("imdbid") imdbid: String,
            @Query("title") title: String,
            @Query("poster") poster: String,
            @Query("plot") plot: String,
            @Query("boxoffice") boxoffice: String
        ): MovieSearchResponse

        annotation class MovieSearchResponse
}
