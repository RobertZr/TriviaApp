package com.example.triviaapp.repository

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.triviaapp.R
import com.example.triviaapp.ui.fragment.QuizFragment
import com.example.triviaapp.ui.fragment.QuizOptionsFragment
import com.example.triviaapp.ui.fragment.ResultFragment
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Test
    fun testNavigationToQuizScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val quizOptionsScenario = launchFragmentInContainer<QuizOptionsFragment>()
        quizOptionsScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(this.requireView(), navController)
        }

        onView(withId(R.id.start_quiz)).perform(ViewActions.click())
        Assert.assertEquals(navController.currentDestination?.id, R.id.quizFragment)
    }

    @Test
    fun testNavigationToResultScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val quizScenario = launchFragmentInContainer<QuizFragment>()
        quizScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.quizFragment)
            Navigation.setViewNavController(this.requireView(), navController)
        }

        onView(withId(R.id.quiz_loading)).perform(InstrumentedTestUtils.waitForGone(3000))
        onView(withId(R.id.quiz_scroll_view))
            .perform(ViewActions.swipeUp())
            .perform(InstrumentedTestUtils.waitForView(R.id.submit, 4000))
        onView(withId(R.id.submit)).perform(ViewActions.click())

        Assert.assertEquals(navController.currentDestination?.id, R.id.resultFragment)
    }

    @Test
    fun testNavigationToQuizOptionsScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val resultScenario = launchFragmentInContainer<ResultFragment>()
        resultScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.resultFragment)
            Navigation.setViewNavController(this.requireView(), navController)
        }

        onView(withId(R.id.replay)).perform(ViewActions.click())
        Assert.assertEquals(navController.currentDestination?.id, R.id.quizOptionsFragment)
    }
}