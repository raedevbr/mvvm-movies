package com.raedevbr.movies.data

import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.dto.movies.MoviesItem
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestPopularMovies(): Flow<Resource<Movies>>
    suspend fun requestMovieDetails(id: Long): Flow<Resource<Movies>>
    fun getCachedFavorites(): Flow<Resource<List<MoviesItem>>>
    suspend fun addToFavorite(movie: MoviesItem): Flow<Resource<Boolean>>
    suspend fun removeFromFavorite(id: Long): Flow<Resource<Boolean>>
    suspend fun isFavorite(id: Long): Flow<Resource<MoviesItem?>>
}