package utils

operator fun String.times(x: Int): String {
    return List(x) { this }.joinToString("")
}
