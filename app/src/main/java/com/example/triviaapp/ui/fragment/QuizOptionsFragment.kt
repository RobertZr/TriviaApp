package com.example.triviaapp.ui.fragment

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.triviaapp.R
import com.example.triviaapp.databinding.FragmentQuizOptionsBinding
import com.example.triviaapp.presentation.viewmodels.SharedViewModel
import com.example.triviaapp.presentation.viewmodels.Utils
import com.example.triviaapp.presentation.viewmodels.ViewModelFactory

class QuizOptionsFragment : BaseFragment<FragmentQuizOptionsBinding, ViewModelFactory>() {

    private val viewModel: SharedViewModel by activityViewModels { viewModelFactory }

    override val viewModelFactory: ViewModelFactory
        get() = ViewModelFactory()

    override fun getViewBinding() = FragmentQuizOptionsBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()

        initView()

        binding.startQuiz.setOnClickListener {
            findNavController().navigate(QuizOptionsFragmentDirections.actionQuizOptionsFragmentToQuizFragment())
        }
    }

    private fun initView() {
        setupCategoriesSpinner()
        setupDifficultiesSpinner()
        setupTypesSpinner()
    }

    private fun setupCategoriesSpinner() {
        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                    it,
                    R.array.categories,
                    android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropdownCategories.apply {
            this.adapter = adapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    viewModel.quizOptions.category = Utils.categoryMap[getItemAtPosition(position).toString()]!!
                }
            }
        }
    }

    private fun setupDifficultiesSpinner() {
        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                    it,
                    R.array.difficulties,
                    android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropdownDifficulties.apply {
            this.adapter = adapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    viewModel.quizOptions.difficulty = Utils.difficultyMap[getItemAtPosition(position)]!!
                }
            }
        }
    }

    private fun setupTypesSpinner() {
        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                    it,
                    R.array.types,
                    android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropdownTypes.apply {
            this.adapter = adapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    viewModel.quizOptions.type = Utils.typeMap[getItemAtPosition(position)]!!
                }
            }
        }
    }
}