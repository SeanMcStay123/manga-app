// kotlin logging found here: https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging-jvm
import controllers.CharacterAPI
import io.github.oshai.kotlinlogging.KotlinLogging
import models.Character
import persistence.JSONSerializer
import utils.isValidDescription
import utils.isValidPowerLevel
import utils.isValidRating
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine // alt enter fixed this import bug
import java.io.File // resolves error in character where "XMLSerializer( " File " " was showing as red
import kotlin.system.exitProcess

// private val characterAPI = CharacterAPI(XMLSerializer(File("characters.xml")))
private val characterAPI =
    CharacterAPI(
        JSONSerializer(File("characters.json")),
    ) // links CharacterAPI class to main.kt, I pressed alt+enter to fix import issue
private val logger =
    KotlinLogging.logger {
    } // anything now with the logger added instead of println shows as "INFO" in the terminal, except exitapp as its println still

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
        >""".trimMargin(">"),
    )
    return readNextInt(" > ==>>")
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addCharacter()
            2 -> runListMenu()
            3 -> updateCharacter()
            4 -> deleteCharacter()
            5 -> save()
            6 -> load()
            7 -> archiveCharacter()
            8 -> searchByCharacterName()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addCharacter() { // collects name, rating, series name, description and power level, validating rating/description/power level
    val characterName = readNextLine("Enter a name for the character: ")
    val details = readValidatedCharacterDetails()

    val isAdded =
        characterAPI.add(
            Character(characterName, details.characterRating, details.mangaSeries, false, details.characterDescription, details.powerLevel),
        )
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
        """.trimMargin(">"),
    )
    return readNextInt(" > ==>>")
}

fun runListMenu() {
    when (val option = listMenu()) {
        1 -> listCharacters()
        2 -> println(characterAPI.listActiveCharacters())
        3 -> println(characterAPI.listArchivedCharacters())
        0 -> {} // back to main menu
        else -> println("Invalid option entered: $option")
    }
}

fun deleteCharacter() {
    logger.info { "deleteCharacter() function invoked" }
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

fun updateCharacter() {
    listCharacters()
    if (characterAPI.numberOfCharacters() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the character to update: ")
        if (characterAPI.isValidIndex(indexToUpdate)) {
            val characterName = readNextLine("Enter a name for the character: ")
            val details = readValidatedCharacterDetails()

            val isUpdated =
                characterAPI.updateCharacter(
                    indexToUpdate,
                    Character(
                        characterName,
                        details.characterRating,
                        details.mangaSeries,
                        false,
                        details.characterDescription,
                        details.powerLevel,
                    ),
                )

            if (isUpdated) {
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

private data class CharacterDetails(
    val characterRating: Int,
    val mangaSeries: String,
    val characterDescription: String,
    val powerLevel: Double,
)

private fun readValidatedCharacterDetails(): CharacterDetails {
    var characterRating: Int
    do {
        characterRating = readNextInt("Enter a rating (1-low, 2, 3, 4, 5-high): ")
        if (!isValidRating(characterRating)) {
            println("Rating must be between 1 and 5, please try again")
        }
    } while (!isValidRating(characterRating))

    val mangaSeries = readNextLine("Enter the manga series for the character: ")

    var characterDescription: String
    do {
        characterDescription = readNextLine("Enter a short description for the character: ")
        if (!isValidDescription(characterDescription)) {
            println("Description can't be empty, please try again")
        }
    } while (!isValidDescription(characterDescription))

    var powerLevel: Double
    do {
        powerLevel = readNextDouble("Enter the character's power level (e.g. 9500.5): ")
        if (!isValidPowerLevel(powerLevel)) {
            println("Power level can't be negative, please try again")
        }
    } while (!isValidPowerLevel(powerLevel))

    return CharacterDetails(characterRating, mangaSeries, characterDescription, powerLevel)
}

fun exitApp() {
    println("Exiting...bye")
    exitProcess(0) // 0 means exit code
}

fun main() {
    runMenu()
}
