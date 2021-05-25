package com.example.triviaapp.domain.usecases

import com.example.triviaapp.data.TriviaRepository
import com.example.triviaapp.data.remote.model.Quiz
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result

class FetchQuizUseCase(
        private val triviaRepository: TriviaRepository
) {
    operator fun invoke(amount: Int,
                        category: Int?,
                        difficulty: String?,
                        type: String?): Observable<Result<Quiz>> = triviaRepository
            .fetchQuizItems(amount, category, difficulty, type)
}