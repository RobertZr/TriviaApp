package com.example.triviaapp.data.remote

import com.example.triviaapp.data.remote.model.Quiz
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(".")
    fun fetchQuiz(
            @Query("amount") amount: Int,
            @Query("category") category: Int?,
            @Query("difficulty") difficulty: String?,
            @Query("type") type: String?
    ): Observable<Result<Quiz>>
}