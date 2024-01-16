package com.raedevbr.movies.data.local

import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.MoviesItem
import kotlinx.coroutines.flow.Flow

internal interface LocalDataSource {
    fun getCachedFavorites(): Flow<Resource<List<MoviesItem>>>
    fun isFavorite(id: Long): Flow<Resource<MoviesItem?>>
    suspend fun cacheFavorites(movie: MoviesItem): Resource<Boolean>
    suspend fun removeFromFavorites(id: Long): Resource<Boolean>
}