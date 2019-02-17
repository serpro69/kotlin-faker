package com.github.sergio.igwt.kfaker

import com.github.sergio.igwt.kfaker.ResourceLoader.getResource
import com.github.sergio.igwt.kfaker.ResourceLoader.getResourceAsStream
import java.io.File
import java.io.InputStream
import java.util.Locale
import java.util.regex.Matcher

internal class FakerService @JvmOverloads internal constructor(locale: Locale? = null) {
    private val randomService = RandomService()
    private val curlyBraceRegex = Regex("""#\{(\p{all}+?)\}""")
    internal val dictionary = load(locale)

    internal constructor(locale: String) : this(Locale.forLanguageTag(locale))

    /**
     * Reads values of the default 'en' locale files into this [dictionary].
     *
     * Additionally `if (locale != null && locale.isValid)`, reads the contents of the specified locale file
     * into this [dictionary] (Will overwrite all matching keys with values from specified locale file.)
     *
     * @throws IllegalArgumentException if the [locale] is invalid or locale dictionary file is not present on the classpath.
     */
    private fun load(locale: Locale? = null): Map<String, Map<String, *>> {
        val defaultValues = LinkedHashMap<String, Map<String, *>>()
        val defaultDir = requireNotNull(getResource("locales/en/")) {
            "Directory with default dictionary files not found"
        }

        File(defaultDir.toURI()).listFiles().forEach {
            if (it.extension == "yml") {
                readCategory(it, Locale.ENGLISH).entries.forEach { category ->
                    if (defaultValues.containsKey(category.key)) {
                        defaultValues.merge(category.key, category.value) { t, u -> t.plus(u) }
                    } else defaultValues[category.key] = category.value
                }
            }
        }

        // TODO: 2/16/2019 see if need to add checks for recurring values here as well and merge the maps
        if (locale != null && locale.toString() != "en") {
            val localeFileStream = requireNotNull(getResourceAsStream("locales/$locale.yml")) {
                "Dictionary file not found for locale value: $locale"
            }

            defaultValues.putAll(readCategory(localeFileStream, locale))
        }

        return defaultValues
    }

    // TODO: 2/15/2019 map result to Dictionary class
    // TODO: 2/15/2019 auto-generate the code for each category (this will allow easy extensibility of the local files)

    private fun readCategory(file: File, locale: Locale): LinkedHashMap<String, Map<String, *>> {
        return readCategory(file.inputStream(), locale)
    }

    @Suppress("UNCHECKED_CAST")
    private fun readCategory(inputStream: InputStream, locale: Locale): LinkedHashMap<String, Map<String, *>> {
        val localeValues = Mapper.readValue(inputStream, Map::class.java)[locale.toString()] as Map<*, *>
        return localeValues["faker"] as LinkedHashMap<String, Map<String, *>>
    }

    /**
     * Returns the category by its [key] name from this [dictionary]
     */
    fun fetchCategory(key: String): Map<String, *> {
        return dictionary[key] ?: throw NoSuchElementException("Category with name '$key' not found")
    }

    /**
     * Returns raw value as [String] from a given [category] fetched by its [key]
     */
    fun getRawValue(category: Map<String, *>, key: String): String {
        val parameterValue = category[key]
            ?: throw NoSuchElementException("Parameter with name '$key' for this category not found")

        return when (parameterValue) {
            is List<*> -> randomService.randomValue(parameterValue) as String
            is String -> parameterValue
            is Map<*, *> -> {
                when {
                    parameterValue.values.all { it is String } -> {
                        val values = parameterValue.values.toList()
                        randomService.randomValue(values) as String
                    }
                    parameterValue.values.all { it is Map<*, *> } -> {
                        val values = parameterValue.values.toList()
                        randomService.randomValue(values.map { "$it" })
                    }
                    else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                }
            }
            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun resolveExpressionWithNumerals(rawValue: String): String {
        return rawValue.map { if (it == '#') randomService.nextInt(10) else it }.joinToString("")
    }

    tailrec fun resolveExpression(category: Map<String, *>, rawExpression: String): String {
        val stringExpression = when {
            curlyBraceRegex.containsMatchIn(rawExpression) -> {
                val sb = StringBuffer()
                findMatchesAndAppendTail(rawExpression, sb, curlyBraceRegex) {
                    it.appendReplacement(sb, getRawValue(category, it.group(1)))
                }
            }
            else -> rawExpression
        }

        return if (!curlyBraceRegex.containsMatchIn(stringExpression)) {
            stringExpression
        } else resolveExpression(category, stringExpression)
    }

    private fun findMatchesAndAppendTail(
        string: String,
        stringBuffer: StringBuffer,
        regex: Regex,
        invoke: (Matcher) -> Unit
    ): String {
        val matcher = regex.toPattern().matcher(string)

        while (matcher.find()) invoke(matcher)

        matcher.appendTail(stringBuffer)
        return stringBuffer.toString()
    }
}
