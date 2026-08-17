import java.lang.System.exit // this resolved the import error

fun mainMenu(): Int {
    println("")
    println("--------------------")
    println("MANGA APP")
    println("--------------------")
    println("MANGA MENU")
    println(" 1) Add a Character")
    println(" 2) List all Characters")
    println(" 3) Update a Character")
    println(" 4) Delete a Character")
    println("--------------------")
    println(" 0) Exit")
    println("--------------------")
    print("==>> ")
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
            else -> println("Invalid option entered: " + option)
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