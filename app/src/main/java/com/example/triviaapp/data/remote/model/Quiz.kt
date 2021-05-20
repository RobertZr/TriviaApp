package com.example.triviaapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Quiz(
        @SerializedName("response_code")
        var responseCode: Int,
        var results: List<QuizItem>
)