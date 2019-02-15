package com.github.sergio.igwt.kfaker

import java.io.*
import java.util.*
import kotlin.collections.LinkedHashMap

internal class FakerService @JvmOverloads internal constructor(locale: Locale? = null) {
    private val dictionary = load(locale)

    internal fun load(locale: Locale? = null): Map<String, Map<String, *>> {
        val defaultValues = LinkedHashMap<String, Map<String, *>>()
        val defaultDir = this::class.java.classLoader.getResource("locales/en/")

        File(defaultDir.toURI()).listFiles().forEach {
            if (it.extension == "yml") defaultValues.putAll(readCategory(it, Locale.ENGLISH))
        }

        if (locale != null && locale.toString() != "en") {
            val localeFileStream = this::class.java.classLoader.getResourceAsStream("locales/$locale.yml")
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
}
