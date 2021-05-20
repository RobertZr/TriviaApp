package com.example.triviaapp.data

import android.content.Context
import androidx.room.Room
import com.example.triviaapp.data.local.TriviaDatabase

class AppContainer(private val context: Context) {
    @Volatile
    private var INSTANCE: TriviaDatabase? = null
    fun getInstance(): TriviaDatabase {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        TriviaDatabase::class.java,
                        "trivia_database"
                )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}