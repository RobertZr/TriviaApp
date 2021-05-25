package com.example.triviaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.triviaapp.data.local.entity.LatestQuiz
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QuizItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(latestQuiz: List<LatestQuiz>): Completable

    @Query("SELECT * FROM latest_quiz")
    fun getLatestQuiz(): Single<List<LatestQuiz>>

    @Query("DELETE FROM latest_quiz")
    fun delete(): Completable
}