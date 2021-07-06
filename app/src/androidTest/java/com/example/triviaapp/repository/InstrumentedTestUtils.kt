package com.example.triviaapp.repository

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import com.example.triviaapp.data.remote.model.QuizItem
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any
import java.util.concurrent.TimeoutException

class InstrumentedTestUtils {
    companion object {
        const val FIRST_FAKE_CATEGORY = "First fake category"
        const val SECOND_FAKE_CATEGORY = "Second fake category"
        const val BOOLEAN_FAKE_TYPE = "True / False"
        const val MULTIPLE_FAKE_TYPE = "multiple"
        const val FIRST_FAKE_DIFFICULTY = "First fake difficulty"
        const val SECOND_FAKE_DIFFICULTY = "Second fake difficulty"
        const val FIRST_FAKE_QUESTION = "First fake question"
        const val SECOND_FAKE_QUESTION = "Second fake question"
        const val FIRST_FAKE_CORRECT = "First fake correct"
        const val SECOND_FAKE_CORRECT = "Second fake correct"
        val MULTIPLE_FAKE_INCORRECT = listOf("First fake 1", "First fake 2", "First fake 3")
        val BOOLEAN_FAKE_INCORRECT = listOf("Second fake")
        val FAKE_QUIZ_LIST = listOf(
                QuizItem(
                        category = FIRST_FAKE_CATEGORY, type = BOOLEAN_FAKE_TYPE, difficulty = FIRST_FAKE_DIFFICULTY,
                        question = FIRST_FAKE_QUESTION, correct = FIRST_FAKE_CORRECT, incorrect = BOOLEAN_FAKE_INCORRECT
                ),
                QuizItem(
                        category = FIRST_FAKE_CATEGORY, type = MULTIPLE_FAKE_TYPE, difficulty = FIRST_FAKE_DIFFICULTY,
                        question = FIRST_FAKE_QUESTION, correct = FIRST_FAKE_CORRECT, incorrect = MULTIPLE_FAKE_INCORRECT
                ),
                QuizItem(
                        category = SECOND_FAKE_CATEGORY, type = MULTIPLE_FAKE_TYPE, difficulty = SECOND_FAKE_DIFFICULTY,
                        question = SECOND_FAKE_QUESTION, correct = SECOND_FAKE_CORRECT, incorrect = MULTIPLE_FAKE_INCORRECT
                ),
                QuizItem(
                        category = SECOND_FAKE_CATEGORY, type = BOOLEAN_FAKE_TYPE, difficulty = SECOND_FAKE_DIFFICULTY,
                        question = SECOND_FAKE_QUESTION, correct = SECOND_FAKE_CORRECT, incorrect = BOOLEAN_FAKE_INCORRECT
                ),
                QuizItem(
                        category = FIRST_FAKE_CATEGORY, type = MULTIPLE_FAKE_TYPE, difficulty = FIRST_FAKE_DIFFICULTY,
                        question = SECOND_FAKE_QUESTION, correct = SECOND_FAKE_CORRECT, incorrect = MULTIPLE_FAKE_INCORRECT
                ),
                QuizItem(
                        category = FIRST_FAKE_CATEGORY, type = BOOLEAN_FAKE_TYPE, difficulty = FIRST_FAKE_DIFFICULTY,
                        question = SECOND_FAKE_QUESTION, correct = SECOND_FAKE_CORRECT, incorrect = BOOLEAN_FAKE_INCORRECT
                ),
                QuizItem(
                        category = SECOND_FAKE_CATEGORY, type = MULTIPLE_FAKE_TYPE, difficulty = SECOND_FAKE_DIFFICULTY,
                        question = FIRST_FAKE_QUESTION, correct = FIRST_FAKE_CORRECT, incorrect = MULTIPLE_FAKE_INCORRECT
                ),
                QuizItem(
                        category = SECOND_FAKE_CATEGORY, type = BOOLEAN_FAKE_TYPE, difficulty = SECOND_FAKE_DIFFICULTY,
                        question = FIRST_FAKE_QUESTION, correct = FIRST_FAKE_CORRECT, incorrect = BOOLEAN_FAKE_INCORRECT
                )
        )

        fun createQuizItem(
                category : String,
                type : String,
                difficulty : String,
                question : String,
                correct : String,
                incorrect : List<String>
        ) : QuizItem {
            return QuizItem(category, type, difficulty, question, correct, incorrect)
        }

        fun waitForView(viewId: Int, millis: Long) = object : ViewAction {
            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> during $millis millis."
            }

            override fun getConstraints(): Matcher<View> {
                return withEffectiveVisibility(Visibility.VISIBLE)
            }

            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = withId(viewId)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        if (viewMatcher.matches(child)) {
                            uiController?.loopMainThreadForAtLeast(500)
                            return
                        }
                    }

                    uiController?.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }

        fun waitForGone(timeout: Long) = object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return any(View::class.java)
            }

            override fun getDescription(): String {
                return "wait up to $timeout milliseconds for the view to be gone"
            }

            override fun perform(uiController: UiController, view: View) {

                val endTime = System.currentTimeMillis() + timeout

                do {
                    if (view.visibility == View.GONE) return
                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                throw PerformException.Builder()
                    .withActionDescription(description)
                    .withCause(TimeoutException("Waited $timeout milliseconds"))
                    .withViewDescription(HumanReadables.describe(view))
                    .build()
            }
        }
    }
}