package com.example.triviaapp.data.remote

import com.example.triviaapp.data.TriviaDataSource
import com.example.triviaapp.data.remote.model.Quiz
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface RemoteDataSource : TriviaDataSource {

    fun fetchQuizItems(
            amount: Int,
            category: Int?,
            difficulty: String?,
            type: String?
    ): Observable<Result<Quiz>>
}