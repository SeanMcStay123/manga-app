package models

data class Character(
    var characterName: String,
    var characterRating: Int,
    var mangaSeries: String,
    var isCharacterArchived: Boolean,
    // short bio for the character
    var characterDescription: String,
    // decimal power/strength rating
    var powerLevel: Double,
)
