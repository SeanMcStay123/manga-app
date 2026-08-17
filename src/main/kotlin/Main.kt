import java.lang.System.exit // this resolved the import error

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
        > ==>> """.trimMargin(">")
    )
    return readlnOrNull()?.toIntOrNull() ?: -1
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
    println("You chose Add Character")
}

fun listCharacters() {
    println("You chose List Characters")
}

fun updateCharacter() {
    println("You chose Update Character")
}

fun deleteCharacter() {
    println("You chose Delete Character")
}

fun exitApp() {
    println("Exiting...bye")
    exit(0) // 0 means exit code
}


fun main() {
    runMenu()
}