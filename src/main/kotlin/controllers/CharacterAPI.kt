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
}