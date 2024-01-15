package com.raedevbr.movies.data.remote

import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.data.error.NETWORK_ERROR
import com.raedevbr.movies.data.error.NO_INTERNET_CONNECTION
import com.raedevbr.movies.data.remote.service.MoviesService
import com.raedevbr.movies.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) : RemoteDataSource {
    override suspend fun requestPopularMovies(): Resource<Movies> {
        val moviesService = serviceGenerator.createService(MoviesService::class.java)
        return when (val response = processCall(moviesService::fetchPopularMovies)) {
            is Movies -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestMovieDetails(id: Long): Resource<Movies> {
        // TODO
        return Resource.DataError(errorCode = 404)
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body() as? Movies ?: -1
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}