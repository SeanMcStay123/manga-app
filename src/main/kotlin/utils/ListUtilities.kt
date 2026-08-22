package utils

fun isValidListIndex(index: Int, list: List<Any>): Boolean { // utility method to determine if an index is valid in a list, moved here so it can be reused across projects
    return (index >= 0 && index < list.size)
}