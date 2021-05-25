package com.example.triviaapp.data.local

import com.example.triviaapp.data.local.dao.QuizItemsDao
import com.example.triviaapp.data.local.entity.LatestQuiz

class LocalDataSourceImpl(database: TriviaDatabase) : LocalDataSource {

    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(triviaDatabase: TriviaDatabase): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSourceImpl(triviaDatabase)
            }
            return INSTANCE!!
        }
    }

    private val quizCache: QuizItemsDao = database.quizItemsDao()

    override fun cacheQuizItems(quiz: List<LatestQuiz>) = quizCache.insert(quiz)

    override fun fetchLocalQuiz() = quizCache.getLatestQuiz()

    override fun deleteLocalQuiz() = quizCache.delete()
}