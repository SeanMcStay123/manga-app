package controllers

import models.Character
import persistence.Serializer


class CharacterAPI(serializerType: Serializer){

    private var characters = ArrayList<Character>()
    private var serializer: Serializer = serializerType


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
    fun numberOfArchivedCharacters(): Int { // helper method to help decide how many archived characters there are
        return characters.stream()
            .filter { character: Character -> character.isCharacterArchived }
            .count()
            .toInt()
    }
    fun numberOfActiveCharacters(): Int {
        return characters.stream()
            .filter { character: Character -> !character.isCharacterArchived }
            .count()
            .toInt()
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
    fun numberOfCharactersByRating(rating: Int): Int {
        return characters.stream()
            .filter { character: Character -> character.characterRating == rating }
            .count()
            .toInt()
    }
    fun findFavoriteCharacter(): Character? { // returns the character with the highest characterRating, or null if empty
        // uses maxByOrNull(), source https://kotlinlang.org/docs/collection-aggregate.html
        return characters.maxByOrNull { it.characterRating }
    }

    fun listCharactersByMangaSeries(series: String): String { // uses filter() with a lambda predicate, source https://kotlinlang.org/docs/collection-filtering.html
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


    fun deleteCharacter(indexToDelete: Int): Character? { // verifies index first, then removes and returns the deleted character (null if invalid)
        return if (isValidListIndex(indexToDelete, characters)) {
            characters.removeAt(indexToDelete)
        } else null
    }

    fun updateCharacter(indexToUpdate: Int, character: Character?): Boolean { // finds the character by index, then overwrites its fields with the new details
        val foundCharacter = findCharacter(indexToUpdate)

        if ((foundCharacter != null) && (character != null)) {// if the character exists, use the character details passed as parameters to update the found character in the ArrayList.

            foundCharacter.characterName = character.characterName
            foundCharacter.characterRating = character.characterRating
            foundCharacter.mangaSeries = character.mangaSeries
            return true // update succeeded
        }


        return false // character not found, update failed
    }

    fun archiveCharacter(indexToArchive: Int): Boolean {
        val foundCharacter = findCharacter(indexToArchive)
        if (foundCharacter != null && !foundCharacter.isCharacterArchived) {
            foundCharacter.isCharacterArchived = true
            return true
        }
        return false
    }

    fun isValidIndex(index: Int): Boolean { // wraps isValidListIndex so Main.kt can check an index before prompting for update details
        return isValidListIndex(index, characters)
    }

    @Throws(Exception::class)
    fun load() {
        characters = serializer.read() as ArrayList<Character>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(characters)
    }

}
