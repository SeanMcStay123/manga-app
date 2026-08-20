package controllers

import models.Character


class CharacterAPI {
    private var characters = ArrayList<Character>()

    fun add(character: Character): Boolean {
        return characters.add(character) // ArrayList.add() is built-in, returns true if the item was added successfully
    }

    fun listAllCharacters(): String {
        return if (characters.isEmpty()) {
            "No characters stored"
        } else {
            var listOfCharacters = ""
            for (i in characters.indices) {
                listOfCharacters += "${i}: ${characters[i]} \n"
            }
            listOfCharacters
        }

    }


    fun numberOfCharacters(): Int { // added two helper methods to help with CharacterAPITest
        return characters.size
    }

    fun findCharacter(index: Int): Character? {
        return if (isValidListIndex(index, characters)) {
            characters[index]
        } else null
    }

    // utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
    fun listActiveCharacters(): String { // returns only characters where isCharacterArchived is false
        return if (numberOfActiveCharacters() == 0) {
            "No active characters stored"
        } else {
            var listOfActiveCharacters = ""
            for (i in characters.indices) {
                if (!characters[i].isCharacterArchived) { // skip archived ones
                    listOfActiveCharacters += "${i}: ${characters[i]} \n"
                }
            }
            listOfActiveCharacters
        }
    }
    fun listArchivedCharacters(): String { // returns only characters where isCharacterArchived is true
        return if (numberOfArchivedCharacters() == 0) {
            "No archived characters stored"
        } else {
            var listOfArchivedCharacters = ""
            for (i in characters.indices) {
                if (characters[i].isCharacterArchived) { // only archived ones
                    listOfArchivedCharacters += "${i}: ${characters[i]} \n"
                }
            }
            listOfArchivedCharacters
        }
    }
    fun numberOfArchivedCharacters(): Int {  // helper method to help decide how many archived characters there are
        var count = 0
        for (character in characters) {
            if (character.isCharacterArchived) {
                count++
            }
        }
        return count
    }
    fun numberOfActiveCharacters(): Int {  // helper method to help decide how many active characters there are
        var count = 0
        for (character in characters) {
            if (!character.isCharacterArchived) {
                count++
            }
        }
        return count
    }
    fun listCharactersBySelectedRating(rating: Int): String {
        return if (numberOfCharactersByRating(rating) == 0) {
            "No characters with rating ${rating} stored"
        } else {
            var listOfCharacters = ""
            for (i in characters.indices) {
                if (characters[i].characterRating == rating) {
                    listOfCharacters += "${i}: ${characters[i]} \n"
                }
            }
            listOfCharacters
        }
    }
    fun numberOfCharactersByRating(rating: Int): Int {  // helper method to decide how many characters there are of a specific rating
        var count = 0
        for (character in characters) {
            if (character.characterRating == rating) {
                count++
            }
        }
        return count
    }
    fun findFavoriteCharacter(): Character? {
        // returns the character with the highest characterRating, or null if empty
        // uses maxByOrNull(), see: https://kotlinlang.org/docs/collection-aggregate.html
        return characters.maxByOrNull { it.characterRating }
    }

    fun listCharactersByMangaSeries(series: String): String {
        // uses filter() with a lambda predicate, see: https://kotlinlang.org/docs/collection-filtering.html
        val matches = characters.filter { it.mangaSeries.equals(series, ignoreCase = true) }
        return if (matches.isEmpty()) {
            "No characters found for manga series: $series"
        } else {
            var listOfCharacters = ""
            for (i in matches.indices) {
                listOfCharacters += "${i}: ${matches[i]} \n"
            }
            listOfCharacters
        }
    }

    fun numberOfCharactersInSeries(series: String): Int {
        // uses count() with a predicate, see: https://kotlinlang.org/docs/collection-aggregate.html
        return characters.count { it.mangaSeries.equals(series, ignoreCase = true) }
    }
}