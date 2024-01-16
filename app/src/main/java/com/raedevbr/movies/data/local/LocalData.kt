package com.raedevbr.movies.data.local

import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.data.error.CACHE_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LocalData @Inject constructor(
    private val movieDao: MovieDao
) : LocalDataSource {
    override fun getCachedFavorites(): Flow<Resource<List<MoviesItem>>> {
        return movieDao.getAllMovies().map { cachedMovies ->
            Resource.Success(data = cachedMovies)
        }
    }

    override suspend fun cacheFavorites(movie: MoviesItem): Resource<Boolean> {
        return try {
            movieDao.insertMovie(movie)
            Resource.Success(data = true)
        } catch (e: IOException) {
            Resource.DataError(errorCode = CACHE_ERROR)
        }
    }

    override suspend fun removeFromFavorites(id: Long): Resource<Boolean> {
        return try {
            movieDao.deleteMovieById(id)
            Resource.Success(data = true)
        } catch (e: IOException) {
            Resource.DataError(errorCode = CACHE_ERROR)
        }
    }

    override suspend fun isFavorite(id: Long): Resource<MoviesItem?> {
        return try {
            val movie = movieDao.getMovieById(id)
            Resource.Success(data = movie)
        } catch (e: Exception) {
            Resource.DataError(errorCode = CACHE_ERROR)
        }
    }


}