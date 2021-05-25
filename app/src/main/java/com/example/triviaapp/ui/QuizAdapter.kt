package com.example.triviaapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.data.remote.model.QuizItem
import com.example.triviaapp.databinding.RecyclerviewQuizItemBinding

class QuizAdapter(
        private var dataSet: List<QuizItem>
) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    private val hasAnswersAdded = mutableMapOf<String, String>()

    fun getAnswers(): MutableMap<String, String> {
        return hasAnswersAdded
    }

    inner class ViewHolder(val binding: RecyclerviewQuizItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewQuizItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataSet[position]) {
                binding.quizQuestion.text = this.question

                if (!hasAnswersAdded.containsKey(this.question)) {
                    var rbn: RadioButton
                    val answers = shuffleAnswers(this.correct, this.incorrect)
                    for (element in answers) {
                        rbn = RadioButton(binding.root.context).apply {
                            id = View.generateViewId()
                            text = element
                            setOnClickListener {
                                hasAnswersAdded[dataSet[position].question] = this.text.toString()
                            }
                        }
                        binding.quizAnswers.addView(rbn)
                    }
                    hasAnswersAdded[this.question] = "X"
                    answers.clear()
                }
            }
        }
    }

    private fun shuffleAnswers(correct: String, wrong: List<String>): MutableList<String> {
        val newList = mutableListOf<String>()
        newList.addAll(wrong)
        newList.add(correct)
        newList.shuffle()
        return newList
    }

    fun updateQuizList(quizList: List<QuizItem>) {
        val oldList = dataSet
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                QuizItemDiffCallback(
                        oldList,
                        quizList
                )
        )
        dataSet = quizList
        diffResult.dispatchUpdatesTo(this)
    }

    class QuizItemDiffCallback(
            var oldQuizList: List<QuizItem>,
            var newQuizList: List<QuizItem>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldQuizList[oldItemPosition].question == newQuizList[newItemPosition].question
        }

        override fun getOldListSize(): Int {
            return oldQuizList.size
        }

        override fun getNewListSize(): Int {
            return newQuizList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldQuizList[oldItemPosition] == newQuizList[newItemPosition]
        }

    }
}