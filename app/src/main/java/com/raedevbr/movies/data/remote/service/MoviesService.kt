package com.raedevbr.movies.data.remote.service

import com.raedevbr.movies.BuildConfig
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.dto.movies.MoviesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale

interface MoviesService {
    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = Locale.getDefault().toLanguageTag()
    ): Response<Movies>

//    @GET("movie/{movie_id}")
//    suspend fun fetchMovieDetails(
//        @Path("movie_id") movieId: Long,
//        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
//        @Query("language") language: String = Locale.getDefault().toLanguageTag()
//    ): Response<List<TODO>>
}