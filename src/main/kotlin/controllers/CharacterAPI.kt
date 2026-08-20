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
    fun numberOfArchivedCharacters(): Int {
// helper method to help decide how many archived characters there are
        var count = 0
        for (character in characters) {
            if (character.isCharacterArchived) {
                count++
            }
        }
        return count
    }
    fun numberOfActiveCharacters(): Int {
// helper method to help decide how many active characters there are
        var count = 0
        for (character in characters) {
            if (!character.isCharacterArchived) {
                count++
            }
        }
        return count
    }
}