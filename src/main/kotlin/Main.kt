import controllers.CharacterAPI
import java.lang.System.exit // this resolved the import error
import utils.readNextInt
import io.github.oshai.kotlinlogging.KotlinLogging // kotlin logging found here: https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging-jvm
import models.Character
import persistence.JSONSerializer
import persistence.XMLSerializer // imports the new character api with XMLSerializer to stop errors
import utils.readNextLine // alt enter fixed this import bug
import java.io.File // resolves error in character where "XMLSerializer( " File " " was showing as red


//private val characterAPI = CharacterAPI(XMLSerializer(File("characters.xml")))
private val characterAPI = CharacterAPI(JSONSerializer(File("characters.json"))) // links CharacterAPI class to main.kt, I pressed alt+enter to fix import issue
private val logger = KotlinLogging.logger {} // anything now with the logger added instead of println shows as "INFO" in the terminal, except exitapp as its println still

fun mainMenu(): Int { // I updated list all characters to just list characters
    print(
        """
        > --------------------------------
        > | MANGA APP                     |
        > --------------------------------
        > | MANGA MENU                    |
        > |  1) Add a Character           |
        > |  2) List Characters           |
        > |  3) Update a Character        |
        > |  4) Delete a Character        |
        > |  5) Save Characters           |
        > |  6) Load Characters           |
        > |  7) Archive a Character       |
        > |  8) Search Characters by Name |
        > --------------------------------
        > |  0) Exit                      |
        > --------------------------------
        >""".trimMargin(">"))
    return readNextInt(" > ==>>")

}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addCharacter()
            2 -> runListMenu()
            3 -> updateCharacter()
            4 -> deleteCharacter()
            5 -> save()
            6 -> load()
            7 -> archiveCharacter()
            8 -> searchByCharacterName()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addCharacter() { // collects name, rating and series name
    val characterName = readNextLine("Enter a name for the character: ")
    val characterRating = readNextInt("Enter a rating (1-low, 2, 3, 4, 5-high): ")
    val mangaSeries = readNextLine("Enter the manga series for the character: ")
    val isAdded = characterAPI.add(Character(characterName, characterRating, mangaSeries, false)) // the false stops the characters from being archived if they are new,
    // isAdded holds the Boolean from ArrayList.add() above, used below to print success/fail

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun listCharacters() {
    println(characterAPI.listAllCharacters())
}

fun listMenu(): Int { // new submenu added  and display when you press the new ListCharacters option
    print(
        """ 
        > -----------------------------
        > |   LIST MENU                |
        > -----------------------------
        > | 1) List All Characters     |
        > | 2) List Active Characters  |
        > | 3) List Archived Characters|
        > -----------------------------
        > | 0) Back                    |
        > -----------------------------
        """.trimMargin(">"))
    return readNextInt(" > ==>>")
}

fun runListMenu() {
    val option = listMenu()
    when (option) {
        1 -> listCharacters()
        2 -> println(characterAPI.listActiveCharacters())
        3 -> println(characterAPI.listArchivedCharacters())
        0 -> {} // back to main menu
        else -> println("Invalid option entered: ${option}")
    }
}

fun deleteCharacter(){ // logger.info { "deleteCharacter() function invoked" }
    listCharacters()
    if (characterAPI.numberOfCharacters() > 0) { // only asks the user to choose the character to delete if characters exist
        val indexToDelete = readNextInt("Enter the index of the character to delete: ")
        val characterToDelete = characterAPI.deleteCharacter(indexToDelete)
        if (characterToDelete != null) {
            println("Delete Successful! Deleted character: ${characterToDelete.characterName}")
        } else {
            println("Delete NOT Successful")
        }
    }
}
    fun updateCharacter() {  // logger.info { "updateCharacter() function invoked" }
        listCharacters()
        if (characterAPI.numberOfCharacters() > 0) { // only ask the user to choose the character if characters exist
            val indexToUpdate = readNextInt("Enter the index of the character to update: ")
            if (characterAPI.isValidIndex(indexToUpdate)) {
                val characterName = readNextLine("Enter a name for the character: ")
                val characterRating = readNextInt("Enter a rating (1-low, 2, 3, 4, 5-high): ")
                val mangaSeries = readNextLine("Enter the manga series for the character: ")

                // passes the index of the character and the new details to CharacterAPI for updating and check for success.
                if (characterAPI.updateCharacter(indexToUpdate, Character(characterName, characterRating, mangaSeries, false))) {
                    println("Update Successful")
                } else {
                    println("Update Failed")
                }
            } else {
                println("There are no characters for this index number")
            }
        }
    }

fun save() { // writes the character collection to file using the serializer
    try {
        characterAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() { // reads the character collection and replaces whats in memory
    try {
        characterAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun archiveCharacter() {
    println(characterAPI.listActiveCharacters())
    if (characterAPI.numberOfActiveCharacters() > 0) {
        val indexToArchive = readNextInt("Enter the index of the character to archive: ")
        if (characterAPI.archiveCharacter(indexToArchive)) {
            println("Archive Successful")
        } else {
            println("Archive Failed")
        }
    }
}

fun searchByCharacterName() {
    val searchName = readNextLine("Enter the name (or part of the name) to search for: ")
    val foundCharacters = characterAPI.searchByCharacterName(searchName)
    if (foundCharacters.isEmpty()) {
        println("No characters found")
    } else {
        println(foundCharacters)
    }
}

fun exitApp() {
    println("Exiting...bye")
    exit(0) // 0 means exit code
}

fun main() {
    runMenu()
}

