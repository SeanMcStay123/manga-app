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

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}