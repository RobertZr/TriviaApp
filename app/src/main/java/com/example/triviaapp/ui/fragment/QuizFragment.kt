package com.example.triviaapp.ui.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.triviaapp.data.remote.model.QuizItem
import com.example.triviaapp.databinding.FragmentQuizBinding
import com.example.triviaapp.presentation.viewmodels.SharedViewModel
import com.example.triviaapp.presentation.viewmodels.ViewModelFactory
import com.example.triviaapp.ui.QuizAdapter

class QuizFragment : BaseFragment<FragmentQuizBinding, ViewModelFactory>() {

    private val viewModel: SharedViewModel by activityViewModels { viewModelFactory }

    private val quizQuestion = mutableListOf<QuizItem>()

    override val viewModelFactory: ViewModelFactory
        get() = ViewModelFactory()

    override fun getViewBinding() = FragmentQuizBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()

        val adapter = QuizAdapter(quizQuestion)
        binding.quizRecyclerView.adapter = adapter

        viewModel.loadingLiveData.observe(this, Observer {
            binding.quizLoading.visibility = it
            when (it) {
                View.VISIBLE -> binding.next.visibility = View.GONE
                View.GONE -> binding.next.visibility = View.VISIBLE
            }
        })

        viewModel.errorLiveData.observe(this, Observer {
            binding.quizError.text = it
        })

        viewModel.quizLiveData.observe(this, Observer {
            adapter.updateQuizList(it.results)
        })

        binding.next.setOnClickListener {
            viewModel.calculateResult(adapter.getAnswers())
            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToResultFragment())
        }
    }

    override fun onStop() {
        viewModel.cacheQuiz(viewModel.quizLiveData.value!!)
        super.onStop()
    }

    override fun onStart() {
        viewModel.fetchQuiz()
        super.onStart()
    }
}