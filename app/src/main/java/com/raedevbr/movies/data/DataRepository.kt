package com.raedevbr.movies.data

import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.data.local.LocalData
import com.raedevbr.movies.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {
    override suspend fun requestPopularMovies(): Flow<Resource<Movies>> {
        return flow {
            emit(remoteRepository.requestPopularMovies())
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestMovieDetails(id: Long): Flow<Resource<Movies>> {
        return flow {
            emit(remoteRepository.requestMovieDetails(id))
        }.flowOn(ioDispatcher)
    }

    override fun getCachedFavorites(): Flow<Resource<List<MoviesItem>>> {
        return localRepository.getCachedFavorites()
    }

    override fun isFavorite(id: Long): Flow<Resource<MoviesItem?>> {
        return localRepository.isFavorite(id)
    }

    override suspend fun addToFavorite(movie: MoviesItem): Flow<Resource<Boolean>> {
        return flow {
            val result = localRepository.cacheFavorites(movie)
            val successData = result.data ?: false
            emit(Resource.Success(data = successData))
        }.flowOn(ioDispatcher)
    }

    override suspend fun removeFromFavorite(id: Long): Flow<Resource<Boolean>> {
        return flow {
            val result = localRepository.removeFromFavorites(id)
            val successData = result.data ?: false
            emit(Resource.Success(data = successData))
        }.flowOn(ioDispatcher)
    }
}