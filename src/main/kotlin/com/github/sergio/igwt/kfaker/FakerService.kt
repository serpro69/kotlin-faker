package com.github.sergio.igwt.kfaker

import com.github.sergio.igwt.kfaker.ResourceLoader.getResource
import com.github.sergio.igwt.kfaker.ResourceLoader.getResourceAsStream
import java.io.File
import java.io.InputStream
import java.util.Locale

internal class FakerService @JvmOverloads internal constructor(locale: Locale? = null) {
    internal val dictionary = load(locale)

    internal constructor(locale: String) : this(Locale.forLanguageTag(locale))

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
}
