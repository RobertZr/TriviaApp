package com.example.triviaapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triviaapp.data.local.entity.LatestQuiz
import com.example.triviaapp.data.local.entity.mapToDomain
import com.example.triviaapp.data.local.entity.mapToLocal
import com.example.triviaapp.data.remote.model.Quiz
import com.example.triviaapp.data.remote.model.QuizItem
import com.example.triviaapp.domain.usecases.CacheQuizUseCase
import com.example.triviaapp.domain.usecases.ClearCacheUseCase
import com.example.triviaapp.domain.usecases.FetchLocalQuizUseCase
import com.example.triviaapp.domain.usecases.FetchQuizUseCase
import com.example.triviaapp.ui.model.QuizOptionsUIModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.Result

class SharedViewModel(
        private val fetchQuizUseCase: FetchQuizUseCase,
        private val fetchLocalQuizUseCase: FetchLocalQuizUseCase,
        private val cacheQuizUseCase: CacheQuizUseCase,
        private val clearCacheUseCase: ClearCacheUseCase
) : ViewModel() {

    val quizOptions: QuizOptionsUIModel = QuizOptionsUIModel()

    val loadingLiveData = MutableLiveData<Int>()
    val quizLiveData = MutableLiveData<Quiz>()
    val errorLiveData = MutableLiveData<String>()
    val resultLiveData = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    var hasQuizCached = false

    fun fetchQuiz() {
        loadingLiveData.postValue(0)
        if (hasQuizCached) fetchLocalQuiz()
        else fetchRemoteQuiz()
    }

    private fun fetchRemoteQuiz() {

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
                        onNext = { handleResult(it) },
                        onComplete = { loadingLiveData.postValue(8) }
                )
                .addTo(disposables)
    }

    private fun fetchLocalQuiz() {
        fetchLocalQuizUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    loadingLiveData.postValue(8)
                }
                .subscribeBy { quizList ->
                    val x = mutableListOf<QuizItem>()
                    quizList.forEach {
                        x.add(it.mapToDomain())
                    }
                    quizLiveData.postValue(
                            Quiz(99, x)
                    )
                }.addTo(disposables)
    }

    fun cacheQuiz(quiz: Quiz) {
        var id = 0
        val latestQuiz = mutableListOf<LatestQuiz>()
        quiz.results.forEach {
            latestQuiz.add(it.mapToLocal(id))
            id++
        }
        cacheQuizUseCase(latestQuiz).subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(disposables)
        hasQuizCached = true
    }

    fun clearCache() {
        Completable.fromRunnable {
            clearCacheUseCase()
        }.subscribeOn(Schedulers.io())
                .doOnComplete { hasQuizCached = false }
                .subscribe()
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
        var maxScore = 0
        quizLiveData.value?.results?.forEach {
            if (answers.containsKey(it.question)) {
                when (it.difficulty) {
                    "hard" -> {
                        maxScore += 3
                        if (answers[it.question] == it.correct) {
                            score += 3
                        }
                    }
                    "medium" -> {
                        maxScore += 2
                        if (answers[it.question] == it.correct) {
                            score += 2
                        }
                    }
                    "easy" -> {
                        maxScore += 1
                        if (answers[it.question] == it.correct) {
                            score += 1
                        }
                    }
                }
            }
        }
        resultLiveData.postValue("Your score is $score / $maxScore")
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}