package utils

fun isValidRating(rating: Int): Boolean = rating in 1..5 // rating must be between 1 (low) and 5 (high)

fun isValidDescription(description: String): Boolean = description.isNotBlank() // description can't be empty or just whitespace

fun isValidPowerLevel(powerLevel: Double): Boolean = powerLevel >= 0.0 // power level can't be negative
