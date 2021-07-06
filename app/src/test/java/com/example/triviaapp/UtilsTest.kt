package com.example.triviaapp

import com.example.triviaapp.presentation.viewmodels.Utils
import org.junit.Assert
import org.junit.Test

class UtilsTest {

    @Test
    fun getKeyTest() {
        val actualKey = Utils.getKey(Utils.categoryMap, 9)
        val expectedKey = "General Knowledge"
        Assert.assertEquals(expectedKey, actualKey)
    }
}