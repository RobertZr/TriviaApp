package com.example.triviaapp.di

import com.example.triviaapp.TriviaApp
import com.example.triviaapp.data.TriviaRepository
import com.example.triviaapp.data.local.LocalDataSource
import com.example.triviaapp.data.local.LocalDataSourceImpl
import com.example.triviaapp.data.local.TriviaDatabase
import com.example.triviaapp.data.remote.RemoteDataSource
import com.example.triviaapp.data.remote.RemoteDataSourceImpl
import com.example.triviaapp.domain.usecases.CacheQuizUseCase
import com.example.triviaapp.domain.usecases.ClearCacheUseCase
import com.example.triviaapp.domain.usecases.FetchLocalQuizUseCase
import com.example.triviaapp.domain.usecases.FetchQuizUseCase


object Injection {

    fun provideTriviaRepository(): TriviaRepository {
        return TriviaRepository.getInstance(provideLocalDataSource(), provideRemoteDataSource())
    }

    fun provideLocalDataSource(): LocalDataSource = LocalDataSourceImpl.getInstance(provideDataBase())

    fun provideRemoteDataSource(): RemoteDataSource = RemoteDataSourceImpl.getInstance()

    fun provideDataBase(): TriviaDatabase = TriviaApp.appContainer.getInstance()

    fun provideFetchQuizUseCase() = FetchQuizUseCase(provideTriviaRepository())

    fun provideFetchLocalQuizUseCase() = FetchLocalQuizUseCase(provideTriviaRepository())

    fun provideClearCacheUseCase() = ClearCacheUseCase(provideTriviaRepository())

    fun provideCacheQuizUseCase() = CacheQuizUseCase(provideTriviaRepository())
}