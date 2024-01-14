package com.raedevbr.movies.usecase.errors

import com.raedevbr.movies.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}