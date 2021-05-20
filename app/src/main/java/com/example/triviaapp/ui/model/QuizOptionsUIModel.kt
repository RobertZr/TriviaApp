package com.example.triviaapp.ui.model

const val EMPTY_STRING = ""

class QuizOptionsUIModel {
    var amount: Int = 10
    var category: Int = -1
    var difficulty: String = EMPTY_STRING
    var type: String = EMPTY_STRING
}