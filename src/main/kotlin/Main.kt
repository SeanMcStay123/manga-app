import controllers.CharacterAPI
import java.lang.System.exit // this resolved the import error
import utils.readNextInt
import io.github.oshai.kotlinlogging.KotlinLogging // kotlin logging found here: https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging-jvm
import models.Character
import utils.readNextLine // alt enter fixed this import bug

private val characterAPI = CharacterAPI() // links CharacterAPI class to main.kt, I pressed alt+enter to fix import issue
private val logger = KotlinLogging.logger {} // anything now with the logger added instead of println shows as "INFO" in the terminal, except exitapp as its println still

fun mainMenu(): Int {
    print(
        """
        > --------------------------------
        > | MANGA APP                     |
        > --------------------------------
        > | MANGA MENU                    |
        > |  1) Add a Character           |
        > |  2) List all Characters       |
        > |  3) Update a Character        |
        > |  4) Delete a Character        |
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
            2 -> listCharacters()
            3 -> updateCharacter()
            4 -> deleteCharacter()
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
fun updateCharacter() {
    logger.info { "updateCharacter() function invoked" }
}
fun deleteCharacter() {
    logger.info { "deleteCharacter() function invoked" }
}

fun exitApp() {
    println("Exiting...bye")
    exit(0) // 0 means exit code
}

fun main() {
    runMenu()
}