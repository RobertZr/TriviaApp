package com.example.triviaapp.domain.usecases

import com.example.triviaapp.data.TriviaRepository
import com.example.triviaapp.data.local.entity.LatestQuiz

class CacheQuizUseCase(
        private val triviaRepository: TriviaRepository
) {
    operator fun invoke(quiz: List<LatestQuiz>) = triviaRepository
            .cacheQuiz(quiz)
}