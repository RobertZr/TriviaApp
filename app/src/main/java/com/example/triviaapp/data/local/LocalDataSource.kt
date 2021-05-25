package com.example.triviaapp.data.local

import com.example.triviaapp.data.TriviaDataSource
import com.example.triviaapp.data.local.entity.LatestQuiz
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource : TriviaDataSource {
    fun cacheQuizItems(quiz: List<LatestQuiz>): Completable

    fun fetchLocalQuiz(): Single<List<LatestQuiz>>

    fun deleteLocalQuiz(): Completable
}