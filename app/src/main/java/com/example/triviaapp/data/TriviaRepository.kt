package com.example.triviaapp.data

import com.example.triviaapp.data.local.LocalDataSource
import com.example.triviaapp.data.local.entity.LatestQuiz
import com.example.triviaapp.data.remote.RemoteDataSource
import com.example.triviaapp.data.remote.model.Quiz
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result

class TriviaRepository private constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
) {

    companion object {
        private var INSTANCE: TriviaRepository? = null
        fun getInstance(localDataSource: LocalDataSource,
                        remoteDataSource: RemoteDataSource): TriviaRepository {
            if (INSTANCE == null) {
                INSTANCE = TriviaRepository(remoteDataSource, localDataSource)
            }
            return INSTANCE!!
        }
    }

    fun fetchQuizItems(amount: Int,
                       category: Int?,
                       difficulty: String?,
                       type: String?): Observable<Result<Quiz>> {
        return remoteDataSource.fetchQuizItems(amount, category, difficulty, type)
    }

    fun fetchCachedQuiz() = localDataSource.fetchLocalQuiz()

    fun cacheQuiz(quiz: List<LatestQuiz>) = localDataSource.cacheQuizItems(quiz)

    fun clearCache() = localDataSource.deleteLocalQuiz()
}