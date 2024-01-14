package com.raedevbr.movies.data.local

import com.raedevbr.movies.data.Resource
import javax.inject.Inject

class LocalData @Inject constructor() {

    fun getCachedFavorites(): Resource<Set<Long>> {
        return Resource.Success(setOf())
    }

    fun isFavorite(id: Long): Resource<Boolean> {
        return Resource.Success(true)
    }

    fun cacheFavorites(ids: Set<Long>): Resource<Boolean> {
        return Resource.Success(true)
    }

    fun removeFromFavorites(id: Long): Resource<Boolean> {
        return Resource.Success(true)
    }
}