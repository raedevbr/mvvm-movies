package com.raedevbr.movies.di

import com.raedevbr.movies.data.error.mapper.ErrorMapper
import com.raedevbr.movies.data.error.mapper.ErrorMapperSource
import com.raedevbr.movies.usecase.errors.ErrorManager
import com.raedevbr.movies.usecase.errors.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}