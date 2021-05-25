package com.example.triviaapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.triviaapp.data.local.dao.QuizItemsDao
import com.example.triviaapp.data.local.entity.LatestQuiz

@Database(
        entities = [LatestQuiz::class],
        version = 2,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TriviaDatabase : RoomDatabase() {
    abstract fun quizItemsDao(): QuizItemsDao
}