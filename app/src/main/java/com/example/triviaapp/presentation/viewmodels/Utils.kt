package com.example.triviaapp.presentation.viewmodels

object Utils {

    val categoryMap: HashMap<String, Int> = hashMapOf(
            "Any Category" to -1, "General Knowledge" to 9, "Entertainment: Books" to 10,
            "Entertainment: Film" to 11, "Entertainment: Music" to 12, "Entertainment: Musicals &amp; Theaters" to 13,
            "Entertainment: Television" to 14, "Entertainment: Video Games" to 15, "Entertainment: Board Games" to 16,
            "Science &amp; Nature" to 17, "Science: Computers" to 18, "Science: Mathematics" to 19,
            "Mythology" to 20, "Sports" to 21, "Geography" to 22, "History" to 23, "Politics" to 24,
            "Art" to 25, "Celebrities" to 26, "Animals" to 27, "Vehicles" to 28, "Entertainment: Comics" to 29,
            "Science: Gadgets" to 30, "Entertainment: Japanese Anime &amp; Manga" to 31,
            "Entertainment: Cartoon &amp; Animations" to 32
    )

    val difficultyMap: HashMap<String, String> = hashMapOf(
            "Any Difficulty" to "-1", "Easy" to "easy", "Medium" to "medium", "Hard" to "hard"
    )

    val typeMap: HashMap<String, String> = hashMapOf(
            "Any Type" to "-1", "Multiple Choice" to "multiple", "True / False" to "boolean"
    )

    fun <K, V> getKey(map: Map<K, V>, target: V): K? {
        for ((key, value) in map) {
            if (target == value) {
                return key
            }
        }
        return null
    }
}