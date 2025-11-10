package com.example.moviesearch

class MovieRepository(private val MovieSearchApiService: MovieSearchApiService) {


    private val API_KEY = "7010df9e"

    suspend fun getCurrentMovie(id: String): Result {
        return try {
            val response = movieApiService.getCurrentMovie(
                movie = "$id,us",
                appId = API_KEY
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}