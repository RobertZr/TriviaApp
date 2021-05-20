package com.example.triviaapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triviaapp.data.remote.model.Quiz
import com.example.triviaapp.domain.usecases.FetchQuizUseCase
import com.example.triviaapp.ui.model.QuizOptionsUIModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.Result

class SharedViewModel(
        private val fetchQuizUseCase: FetchQuizUseCase
) : ViewModel() {

    val quizOptions: QuizOptionsUIModel = QuizOptionsUIModel()

    val quizLiveData = MutableLiveData<Quiz>()
    val errorLiveData = MutableLiveData<String>()
    val resultLiveData = MutableLiveData<Int>()

    private val disposables = CompositeDisposable()

    fun fetchQuiz() {

        val amount: Int = quizOptions.amount

        val category: Int? = if (quizOptions.category == -1) {
            null
        } else {
            quizOptions.category
        }

        val difficulty: String? = if (quizOptions.difficulty == "-1") {
            null
        } else {
            quizOptions.difficulty
        }

        val type: String? = if (quizOptions.type == "-1") {
            null
        } else {
            quizOptions.type
        }

        fetchQuizUseCase(
                amount,
                category,
                difficulty,
                type
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { handleResult(it) }
                )
                .addTo(disposables)
    }

    private fun handleResult(result: Result<Quiz>) {
        if (!result.isError) {
            result.response()?.let {
                if ((200 until 300).contains(it.code())) {
                    quizLiveData.postValue(it.body())
                } else {
                    errorLiveData.postValue(it.errorBody().toString())
                }
            }
        } else {
            errorLiveData.postValue(result.error().toString())
        }
    }

    fun calculateResult(answers: MutableMap<String, String>) {
        var score = 0
        quizLiveData.value?.results?.forEach {
            if (answers.containsKey(it.question)) {
                if (answers[it.question] == it.correct) {
                    when (it.difficulty) {
                        "hard" -> score += 3
                        "medium" -> score += 2
                        "easy" -> score++
                    }
                }
            }
        }
        resultLiveData.postValue(score)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}