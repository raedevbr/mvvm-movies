package com.raedevbr.movies.data

import com.raedevbr.movies.data.dto.movies.Movies
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestPopularMovies(): Flow<Resource<Movies>>
    suspend fun requestMovieDetails(id: Long): Flow<Resource<Movies>>
    suspend fun addToFavorite(id: Long): Flow<Resource<Boolean>>
    suspend fun removeFromFavorite(id: Long): Flow<Resource<Boolean>>
    suspend fun isFavorite(id: Long): Flow<Resource<Boolean>>
}