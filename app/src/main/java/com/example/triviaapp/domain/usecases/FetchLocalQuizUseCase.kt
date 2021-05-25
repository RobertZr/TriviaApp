package com.example.triviaapp.domain.usecases

import com.example.triviaapp.data.TriviaRepository

class FetchLocalQuizUseCase(
        private val triviaRepository: TriviaRepository
) {
    operator fun invoke() = triviaRepository
            .fetchCachedQuiz()
}