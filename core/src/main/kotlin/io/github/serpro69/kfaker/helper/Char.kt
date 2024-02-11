package io.github.serpro69.kfaker.helper

import io.github.serpro69.kfaker.FakerConfig

internal fun prepare(string: String, config: FakerConfig): String {
    return string.romanize(config)
        .fixUmlauts()
        .fixNordicChars()
        .replace(Regex("[^\\w-]"), "").lowercase()
}

private fun String.fixNordicChars(): String {
    return this.replace(Regex("[æøå]", RegexOption.IGNORE_CASE)) { matchResult ->
        when (matchResult.value.lowercase()) {
            "æ" -> "ae"
            "ø" -> "oe"
            "å" -> "aa"
            else -> matchResult.value.lowercase()
        }
    }
}

private fun String.fixUmlauts(): String {
    return this.replace(Regex("[äöüß]", RegexOption.IGNORE_CASE)) { matchResult ->
        when (matchResult.value.lowercase()) {
            "ä" -> "ae"
            "ö" -> "oe"
            "ü" -> "ue"
            "ß" -> "ss"
            else -> matchResult.value.lowercase()
        }
    }
}

private fun String.romanize(config: FakerConfig): String {
    return when (config.locale) {
        "uk" -> {
            // Based on conventions adopted by BGN/PCGN for Ukrainian
            val ukChars = mapOf(
                // lowercase
                'а' to "a", 'б' to "b", 'в' to "v", 'г' to "h", 'ґ' to "g", 'д' to "d", 'е' to "e", 'є' to "ye",
                'ж' to "zh", 'з' to "z", 'и' to "y", 'і' to "i", 'ї' to "yi", 'й' to "y", 'к' to "k", 'л' to "l",
                'м' to "m", 'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t", 'у' to "u",
                'ф' to "f", 'х' to "kh", 'ц' to "ts", 'ч' to "ch", 'ш' to "sh", 'щ' to "shch", 'ю' to "yu", 'я' to "ya",
                // uppercase
                'А' to "a", 'Б' to "b", 'В' to "v", 'Г' to "h", 'Ґ' to "g", 'Д' to "d", 'Е' to "e", 'Є' to "ye",
                'Ж' to "zh", 'З' to "z", 'И' to "y", 'І' to "i", 'Ї' to "yi", 'Й' to "y", 'К' to "k", 'Л' to "l",
                'М' to "m", 'Н' to "n", 'О' to "o", 'П' to "p", 'Р' to "r", 'С' to "s", 'Т' to "t", 'У' to "u",
                'Ф' to "f", 'Х' to "kh", 'Ц' to "ts", 'Ч' to "ch", 'Ш' to "sh", 'Щ' to "shch", 'Ю' to "yu", 'Я' to "ya",
                // Ignore symbol, because its standard presentation is not allowed in URLs
                'ь' to "",
            )
            this.map { ukChars[it] ?: it.toString() }.joinToString("")
        }
        "ru" -> {
            // Based on conventions adopted by BGN/PCGN for Russian
            val ruChars = mapOf(
                // lowercase
                'а' to "a", 'б' to "b", 'в' to "v", 'г' to "h", 'д' to "d", 'е' to "e", 'ё' to "ye", 'ж' to "zh",
                'з' to "z", 'и' to "i", 'й' to "y", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n", 'о' to "o",
                'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t", 'у' to "u", 'ф' to "f", 'х' to "kh", 'ц' to "ts",
                'ч' to "ch", 'ш' to "sh", 'щ' to "shch", 'ы' to "у", 'э' to "e", 'ю' to "yu", 'я' to "ya",
                // uppercase
                'А' to "a", 'Б' to "b", 'В' to "v", 'Г' to "h", 'Д' to "d", 'Е' to "e", 'Ё' to "ye", 'Ж' to "zh",
                'З' to "z", 'И' to "i", 'Й' to "y", 'К' to "k", 'Л' to "l", 'М' to "m", 'Н' to "n", 'О' to "o",
                'П' to "p", 'Р' to "r", 'С' to "s", 'Т' to "t", 'У' to "u", 'Ф' to "f", 'Х' to "kh", 'Ц' to "ts",
                'Ч' to "ch", 'Ш' to "sh", 'Щ' to "shch", 'Ы' to "у", 'Э' to "e", 'Ю' to "yu", 'Я' to "ya",
                // Ignore symbols, because their standard presentation is not allowed in URLs
                'ь' to "",
                'ъ' to "",
            )
            return this.map { ruChars[it] ?: it.toString() }.joinToString("")
        }
        else -> this
    }
}
