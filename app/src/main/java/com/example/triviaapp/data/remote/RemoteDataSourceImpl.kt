package com.example.triviaapp.data.remote

import com.example.triviaapp.data.remote.model.Quiz
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class RemoteDataSourceImpl constructor(
        private val api: ApiInterface
) : RemoteDataSource {

    companion object {
        private var INSTANCE: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = RemoteDataSourceImpl(RetrofitClient.api)
            }
            return INSTANCE!!
        }
    }

    override fun fetchQuizItems(
            amount: Int,
            category: Int?,
            difficulty: String?,
            type: String?
    ): Observable<Result<Quiz>> {
        return api.fetchQuiz(amount, category, difficulty, type)
    }
}