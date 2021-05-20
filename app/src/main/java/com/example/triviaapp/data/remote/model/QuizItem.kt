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
)