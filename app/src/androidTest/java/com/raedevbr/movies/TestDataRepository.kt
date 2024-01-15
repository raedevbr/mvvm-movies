package com.raedevbr.movies

import com.raedevbr.movies.TestUtil.dataStatus
import com.raedevbr.movies.TestUtil.initData
import com.raedevbr.movies.data.DataRepositorySource
import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.error.NETWORK_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TestDataRepository @Inject constructor() : DataRepositorySource {

    override suspend fun requestPopularMovies(): Flow<Resource<Movies>> {
        return when (dataStatus) {
            DataStatus.Success -> {
                flow { emit(Resource.Success(initData())) }
            }
            DataStatus.Fail -> {
                flow { emit(Resource.DataError<Movies>(errorCode = NETWORK_ERROR)) }
            }
            DataStatus.EmptyResponse -> {
                flow { emit(Resource.Success(Movies(arrayListOf()))) }
            }
        }
    }

    override suspend fun requestMovieDetails(id: Long): Flow<Resource<Movies>> {
        //TODO("Not yet implemented")
        return flow { emit(Resource.Success(Movies(arrayListOf()))) }
    }

    override suspend fun addToFavorite(id: Long): Flow<Resource<Boolean>> {
        return flow { emit(Resource.Success(true)) }
    }

    override suspend fun removeFromFavorite(id: Long): Flow<Resource<Boolean>> {
        return flow { emit(Resource.Success(true)) }
    }

    override suspend fun isFavorite(id: Long): Flow<Resource<Boolean>> {
        return flow { emit(Resource.Success(true)) }
    }
}