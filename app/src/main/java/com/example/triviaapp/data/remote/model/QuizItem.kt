package com.example.triviaapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuizItem(
        @SerializedName("category")
        var category: String,
        @SerializedName("type")
        var type: String,
        @SerializedName("difficulty")
        var difficulty: String,
        @SerializedName("question")
        var question: String,
        @SerializedName("correct_answer")
        var correct: String,
        @SerializedName("incorrect_answers")
        var incorrect: List<String>
) {
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass) return false

        other as QuizItem
        if (category != other.category) return false
        if (type != other.type) return false
        if (difficulty != other.difficulty) return false
        if (question != other.question) return false
        if (correct != other.correct) return false
        if (incorrect.size != other.incorrect.size) return false
        return incorrect.zip(other.incorrect).all { (x, y) -> x == y }
    }
}