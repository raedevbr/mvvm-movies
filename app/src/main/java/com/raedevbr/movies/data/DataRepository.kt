package com.raedevbr.movies.data

import com.raedevbr.movies.data.dto.movies.Movies
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

    override suspend fun addToFavorite(id: Long): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            localRepository.getCachedFavorites().let {
                it.data?.toMutableSet()?.let { set ->
                    val isAdded = set.add(id)
                    if (isAdded) {
                        emit(localRepository.cacheFavorites(set))
                    } else {
                        emit(Resource.Success(false))
                    }
                }
                it.errorCode?.let { errorCode ->
                    emit(Resource.DataError<Boolean>(errorCode))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun removeFromFavorite(id: Long): Flow<Resource<Boolean>> {
        return flow {
            emit(localRepository.removeFromFavorites(id))
        }.flowOn(ioDispatcher)
    }

    override suspend fun isFavorite(id: Long): Flow<Resource<Boolean>> {
        return flow {
            emit(localRepository.isFavorite(id))
        }.flowOn(ioDispatcher)
    }
}