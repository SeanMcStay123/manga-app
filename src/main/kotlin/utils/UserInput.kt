package utils

fun readIntNotNull() = readlnOrNull()?.toIntOrNull() ?: -1

// reads user input from the console and validates it as an Int. If it's not an Int, it returns -1

fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

// This function accepts a prompt as a parameter and prints it to the console (e.g. "Enter the number of items").

fun readNextDouble(prompt: String?): Double {   // same as readNextInt just swapping .toInt to .toDouble(), main differrence is that it can accept 4.5/10 instead of just 4/10
    do {
        try {
            print(prompt)
            return readln().toDouble()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a decimal number please.")
        }
    } while (true)
}

fun readNextFloat(prompt: String?): Float { // same as readNextInt just swapping .toInt to .toFloat()
    do {
        try {
            print(prompt)
            return readln().toFloat()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a decimal number please.")
        }
    } while (true)
}

fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

fun readNextChar(prompt: String?): Char {
    do {
        try {
            print(prompt)
            return readln().first()
        } catch (e: Exception) { // deleted NumberFormat to widen the catch of inputs and give less errors
            System.err.println("\tEnter a character please.")
        }
    } while (true)
}

