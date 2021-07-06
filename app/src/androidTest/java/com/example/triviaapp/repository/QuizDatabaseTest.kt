package com.example.triviaapp.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.triviaapp.data.local.TriviaDatabase
import com.example.triviaapp.data.local.dao.QuizItemsDao
import com.example.triviaapp.data.local.entity.LatestQuiz
import com.example.triviaapp.data.local.entity.mapToLocal
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class QuizDatabaseTest {

    private lateinit var quizDao: QuizItemsDao
    private lateinit var db: TriviaDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TriviaDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        quizDao = db.quizItemsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun quizItemInsertionTest() {
        //given
        val quizItem = InstrumentedTestUtils.createQuizItem(
            InstrumentedTestUtils.FIRST_FAKE_CATEGORY,
            InstrumentedTestUtils.BOOLEAN_FAKE_TYPE,
            InstrumentedTestUtils.FIRST_FAKE_DIFFICULTY,
            InstrumentedTestUtils.FIRST_FAKE_QUESTION,
            InstrumentedTestUtils.FIRST_FAKE_CORRECT,
            InstrumentedTestUtils.BOOLEAN_FAKE_INCORRECT
        )

        //when
        quizDao.insert(listOf(quizItem.mapToLocal(0))).blockingAwait()

        //then
        quizDao.getLatestQuiz().test().assertValue {
            it.isNotEmpty()
        }
    }

    @Test
    fun quizItemMultipleInsertionTest() {
        //given
        val list: MutableList<LatestQuiz> = mutableListOf()
        for (i in InstrumentedTestUtils.FAKE_QUIZ_LIST.indices) {
            list.add(InstrumentedTestUtils.FAKE_QUIZ_LIST[i].mapToLocal(i))
        }

        //when
        quizDao.insert(list).blockingAwait()

        //then
        quizDao.getLatestQuiz().test().assertValue {
            it.size == InstrumentedTestUtils.FAKE_QUIZ_LIST.size
        }
    }

    @Test
    fun cleanDatabaseTest() {
        //given
        val list: MutableList<LatestQuiz> = mutableListOf()
        for (i in InstrumentedTestUtils.FAKE_QUIZ_LIST.indices) {
            list.add(InstrumentedTestUtils.FAKE_QUIZ_LIST[i].mapToLocal(i))
        }
        quizDao.insert(list).blockingAwait()

        //when
        quizDao.delete().blockingAwait()

        //then
        quizDao.getLatestQuiz().test().assertValue { it.isEmpty() }
    }
}