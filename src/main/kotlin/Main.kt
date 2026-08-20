import java.lang.System.exit // this resolved the import error
import utils.readNextInt
import io.github.oshai.kotlinlogging.KotlinLogging // kotlin logging found here: https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging-jvm

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

fun addCharacter() {
    logger.info { " addCharacter() function invoked "}
}
fun listCharacters() {
    logger.info { "listCharacters() function invoked" }
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