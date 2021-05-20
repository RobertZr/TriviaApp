package com.example.triviaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.triviaapp.data.local.entity.LatestQuiz

@Dao
interface QuizItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(latestQuiz: LatestQuiz)

    @Query("SELECT * FROM latest_quiz")
    fun getLatestQuiz(): List<LatestQuiz>

    @Query("DELETE FROM latest_quiz")
    fun delete()
}