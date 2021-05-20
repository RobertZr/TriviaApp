package com.example.triviaapp.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.triviaapp.databinding.FragmentQuizResultBinding
import com.example.triviaapp.presentation.viewmodels.SharedViewModel
import com.example.triviaapp.presentation.viewmodels.ViewModelFactory

class ResultFragment : BaseFragment<FragmentQuizResultBinding, ViewModelFactory>() {

    private val viewModel: SharedViewModel by activityViewModels { viewModelFactory }

    override val viewModelFactory: ViewModelFactory
        get() = ViewModelFactory()

    override fun getViewBinding() = FragmentQuizResultBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()

        viewModel.resultLiveData.observe(this, Observer {
            binding.result.text = viewModel.resultLiveData.value.toString()
        })


        binding.next.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToQuizOptionsFragment())
        }
    }
}