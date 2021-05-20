package com.example.triviaapp.ui.fragment

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

        viewModel.errorLiveData.observe(this, Observer {
            binding.quizError.text = it
        })

        viewModel.quizLiveData.observe(this, Observer {
            quizQuestion.clear()
            quizQuestion.addAll(it.results)
            binding.quizRecyclerView.adapter?.notifyDataSetChanged()
        })

        viewModel.fetchQuiz()

        binding.next.setOnClickListener {
            viewModel.calculateResult(adapter.getAnswers())
            findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToResultFragment())
        }
    }
}