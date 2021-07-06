package com.example.triviaapp

import com.example.triviaapp.data.local.entity.LatestQuiz
import com.example.triviaapp.data.remote.model.Quiz
import com.example.triviaapp.data.remote.model.QuizItem
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

class UnitTestUtils {
    companion object {
        const val FIRST_FAKE_CATEGORY = "First fake category"
        const val SECOND_FAKE_CATEGORY = "Second fake category"
        const val BOOLEAN_FAKE_TYPE = "True / False"
        const val MULTIPLE_FAKE_TYPE = "multiple"
        const val FIRST_FAKE_DIFFICULTY = "easy"
        const val SECOND_FAKE_DIFFICULTY = "medium"
        const val THIRD_FAKE_DIFFICULTY = "hard"
        const val FIRST_FAKE_QUESTION = "First fake question"
        const val SECOND_FAKE_QUESTION = "Second fake question"
        const val FIRST_FAKE_CORRECT = "First fake correct"
        const val SECOND_FAKE_CORRECT = "Second fake correct"
        val MULTIPLE_FAKE_INCORRECT = listOf("First fake 1", "First fake 2", "First fake 3")
        val BOOLEAN_FAKE_INCORRECT = listOf("Second fake")
        val FAKE_QUIZ_LIST = listOf(
            QuizItem(
                category = FIRST_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            ),
            QuizItem(
                category = FIRST_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            QuizItem(
                category = SECOND_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = SECOND_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            QuizItem(
                category = SECOND_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = SECOND_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            ),
            QuizItem(
                category = FIRST_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            QuizItem(
                category = FIRST_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            ),
            QuizItem(
                category = SECOND_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = THIRD_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            QuizItem(
                category = SECOND_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = THIRD_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            )
        )
        val FAKE_LOCAL_QUIZ_LIST = listOf(
            LatestQuiz(
                uuid = 1,
                category = FIRST_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            ),
            LatestQuiz(
                uuid = 2,
                category = FIRST_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            LatestQuiz(
                uuid = 3,
                category = SECOND_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = SECOND_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            LatestQuiz(
                uuid = 4,
                category = SECOND_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = SECOND_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            ),
            LatestQuiz(
                uuid = 5,
                category = FIRST_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            LatestQuiz(
                uuid = 6,
                category = FIRST_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = FIRST_FAKE_DIFFICULTY,
                question = SECOND_FAKE_QUESTION,
                correct = SECOND_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            ),
            LatestQuiz(
                uuid = 7,
                category = SECOND_FAKE_CATEGORY,
                type = MULTIPLE_FAKE_TYPE,
                difficulty = THIRD_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = MULTIPLE_FAKE_INCORRECT
            ),
            LatestQuiz(
                uuid = 8,
                category = SECOND_FAKE_CATEGORY,
                type = BOOLEAN_FAKE_TYPE,
                difficulty = THIRD_FAKE_DIFFICULTY,
                question = FIRST_FAKE_QUESTION,
                correct = FIRST_FAKE_CORRECT,
                incorrect = BOOLEAN_FAKE_INCORRECT
            )
        )
        val OK_RESPONSE_CODE = 0
        val FAKE_QUIZ = Quiz(OK_RESPONSE_CODE, FAKE_QUIZ_LIST)
        val FAKE_RESPONSE: Response<Quiz> = Response.success(FAKE_QUIZ)
        val FAKE_RESULT: Result<Quiz> = Result.response(FAKE_RESPONSE)

        val allCorrectAnswers = mutableMapOf(
            FIRST_FAKE_QUESTION to FIRST_FAKE_CORRECT,
            SECOND_FAKE_QUESTION to SECOND_FAKE_CORRECT
        )
        val allIncorrectAnswers = mutableMapOf(
            FIRST_FAKE_QUESTION to MULTIPLE_FAKE_INCORRECT[1],
            SECOND_FAKE_QUESTION to BOOLEAN_FAKE_INCORRECT[0]
        )
        val mixedAnswers = mutableMapOf(
            FIRST_FAKE_QUESTION to FIRST_FAKE_CORRECT,
            SECOND_FAKE_QUESTION to BOOLEAN_FAKE_INCORRECT[0]
        )
    }
}