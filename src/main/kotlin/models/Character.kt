package models

data class Character(
    var characterName: String,
    var characterRating: Int,
    var mangaSeries: String,
    var isCharacterArchived: Boolean,
    var characterDescription: String, // short bio for the character
    var powerLevel: Double // decimal power/strength rating
) {
}
