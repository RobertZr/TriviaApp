package com.example.triviaapp.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromString(stringList: String): List<String> {
        return stringList.split("<>").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = "<>")
    }
}