package com.example.triviaapp

import android.app.Application
import com.example.triviaapp.data.AppContainer

class TriviaApp : Application() {

    companion object {
        lateinit var appContainer: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(applicationContext)
    }
}