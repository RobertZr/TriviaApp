package com.example.triviaapp.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.triviaapp.RxImmediateSchedulerRule
import com.example.triviaapp.UnitTestUtils
import com.example.triviaapp.domain.usecases.CacheQuizUseCase
import com.example.triviaapp.domain.usecases.ClearCacheUseCase
import com.example.triviaapp.domain.usecases.FetchLocalQuizUseCase
import com.example.triviaapp.domain.usecases.FetchQuizUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SharedViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get: Rule
    val schedulers = RxImmediateSchedulerRule()

    private lateinit var viewModel: SharedViewModel

    val fetchQuizUseCase = mockk<FetchQuizUseCase>()
    val fetchLocalQuizUseCase = mockk<FetchLocalQuizUseCase>()
    private val cacheQuizUseCase = mockk<CacheQuizUseCase>()
    private val clearCacheUseCase = mockk<ClearCacheUseCase>()

    @Before
    fun setup() {
        viewModel = SharedViewModel(
            fetchQuizUseCase,
            fetchLocalQuizUseCase,
            cacheQuizUseCase,
            clearCacheUseCase
        )

        every {
            fetchQuizUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns Observable.just(UnitTestUtils.FAKE_RESULT)
        every { fetchLocalQuizUseCase() } returns Single.just(UnitTestUtils.FAKE_LOCAL_QUIZ_LIST)
    }

    @Test
    fun fetchRemoteQuizTest() {
        //given
        every {
            fetchQuizUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns Observable.just(UnitTestUtils.FAKE_RESULT)
        viewModel.hasQuizCached = false

        //when
        viewModel.fetchQuiz()

        //then
        for (i in viewModel.quizLiveData.value!!.results.indices) {
            Assert.assertEquals(
                viewModel.quizLiveData.value!!.results[i],
                UnitTestUtils.FAKE_QUIZ_LIST[i]
            )
        }
    }

    @Test
    fun fetchLocalQuizTest() {
        //given
        every { fetchLocalQuizUseCase() } returns Single.just(UnitTestUtils.FAKE_LOCAL_QUIZ_LIST)
        viewModel.hasQuizCached = true

        //when
        viewModel.fetchQuiz()

        //then
        for (i in viewModel.quizLiveData.value!!.results.indices) {
            Assert.assertEquals(
                viewModel.quizLiveData.value!!.results[i],
                UnitTestUtils.FAKE_QUIZ_LIST[i]
            )
        }
    }

    @Test
    fun calculateCorrectResult() {
        //given
        val allCorrectAnswers = UnitTestUtils.allCorrectAnswers
        viewModel.fetchQuiz()

        //when
        viewModel.calculateResult(allCorrectAnswers)

        //then
        Assert.assertEquals("Your score is 14 / 14", viewModel.resultLiveData.value)
    }

    @Test
    fun calculateIncorrectResult() {
        //given
        val allIncorrectAnswers = UnitTestUtils.allIncorrectAnswers
        viewModel.fetchQuiz()

        //when
        viewModel.calculateResult(allIncorrectAnswers)

        //then
        Assert.assertEquals("Your score is 0 / 14", viewModel.resultLiveData.value)
    }

    @Test
    fun calculateMixedResult() {
        //given
        val mixedAnswers = UnitTestUtils.mixedAnswers
        viewModel.fetchQuiz()

        //when
        viewModel.calculateResult(mixedAnswers)

        //then
        Assert.assertEquals("Your score is 8 / 14", viewModel.resultLiveData.value)
    }
}