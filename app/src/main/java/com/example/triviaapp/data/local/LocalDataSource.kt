package com.example.triviaapp.data.local

import com.example.triviaapp.data.TriviaDataSource
import com.example.triviaapp.data.remote.model.Quiz

interface LocalDataSource : TriviaDataSource {
    fun cacheQuizItems(quiz: Quiz)

    fun fetchLocalQuiz(): Quiz

    fun deleteLocalQuiz()
}