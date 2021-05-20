package com.example.triviaapp.data.local

import com.example.triviaapp.data.local.dao.QuizItemsDao
import com.example.triviaapp.data.local.entity.mapToDomain
import com.example.triviaapp.data.local.entity.mapToLocal
import com.example.triviaapp.data.remote.model.Quiz

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

    override fun cacheQuizItems(quiz: Quiz) {
        quiz.results.forEach {
            quizCache.insert(it.mapToLocal())
        }
    }

    override fun fetchLocalQuiz(): Quiz {
        return Quiz(
                99,
                quizCache.getLatestQuiz().map { it.mapToDomain() }
        )
    }

    override fun deleteLocalQuiz() {
        quizCache.delete()
    }
}