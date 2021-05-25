package com.example.triviaapp.domain.usecases

import com.example.triviaapp.data.TriviaRepository

class ClearCacheUseCase(
        private val triviaRepository: TriviaRepository
) {
    operator fun invoke() = triviaRepository
            .clearCache()
}