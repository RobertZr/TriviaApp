package com.example.triviaapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.triviaapp.di.Injection

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == SharedViewModel::class.java) {
            return SharedViewModel(Injection.provideFetchQuizUseCase()) as T
        }
        throw IllegalArgumentException("unknown model class $modelClass")
    }
}