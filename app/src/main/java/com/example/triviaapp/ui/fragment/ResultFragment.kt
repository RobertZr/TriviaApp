package com.example.triviaapp.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.triviaapp.R
import com.example.triviaapp.databinding.FragmentQuizResultBinding
import com.example.triviaapp.presentation.viewmodels.SharedViewModel
import com.example.triviaapp.presentation.viewmodels.Utils
import com.example.triviaapp.presentation.viewmodels.ViewModelFactory

class ResultFragment : BaseFragment<FragmentQuizResultBinding, ViewModelFactory>() {

    private val viewModel: SharedViewModel by activityViewModels { viewModelFactory }

    override val viewModelFactory: ViewModelFactory
        get() = ViewModelFactory()

    override fun getViewBinding() = FragmentQuizResultBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()

        viewModel.resultLiveData.observe(this, Observer {
            binding.result.text = viewModel.resultLiveData.value
        })

        binding.quizCategory.text = resources.getString(
                R.string.result_quiz_category,
                Utils.getKey(Utils.categoryMap, viewModel.quizOptions.category)
        )
        binding.quizDifficulty.text = resources.getString(
                R.string.result_quiz_difficulty,
                Utils.getKey(Utils.difficultyMap, viewModel.quizOptions.difficulty)
        )
        binding.quizType.text = resources.getString(
                R.string.result_quiz_type,
                Utils.getKey(Utils.typeMap, viewModel.quizOptions.type)
        )


        binding.replay.setOnClickListener {
            viewModel.clearCache()
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToQuizOptionsFragment())
        }
    }
}