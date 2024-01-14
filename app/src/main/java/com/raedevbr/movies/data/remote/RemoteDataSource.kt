package com.raedevbr.movies.data.remote

import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.Movies

internal interface RemoteDataSource {
    suspend fun requestPopularMovies(): Resource<Movies>
    suspend fun requestMovieDetails(id: Long) : Resource<Movies>
}