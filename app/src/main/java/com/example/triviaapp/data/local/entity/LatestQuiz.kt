package com.example.triviaapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.triviaapp.data.remote.model.QuizItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "latest_quiz")
data class LatestQuiz(
        @PrimaryKey(autoGenerate = true)
        var uuid: Int = 0,

        @ColumnInfo(name = "category")
        var category: String,

        @ColumnInfo(name = "type")
        var type: String,

        @ColumnInfo(name = "difficulty")
        var difficulty: String,

        @ColumnInfo(name = "question")
        var question: String,

        @ColumnInfo(name = "correct")
        @SerializedName("correct_answer")
        var correct: String,

        @ColumnInfo(name = "incorrect")
        @SerializedName("incorrect_answers")
        var incorrect: List<String>
)

fun LatestQuiz.mapToDomain() = QuizItem(
        category = this.category,
        type = this.type,
        difficulty = this.difficulty,
        question = this.question,
        correct = this.correct,
        incorrect = this.incorrect
)

fun QuizItem.mapToLocal() = LatestQuiz(
        category = this.category,
        type = this.type,
        difficulty = this.difficulty,
        question = this.question,
        correct = this.correct,
        incorrect = this.incorrect
)