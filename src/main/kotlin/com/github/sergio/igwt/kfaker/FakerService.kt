package com.github.sergio.igwt.kfaker

import com.github.sergio.igwt.kfaker.ResourceLoader.getResource
import com.github.sergio.igwt.kfaker.ResourceLoader.getResourceAsStream
import com.github.sergio.igwt.kfaker.dictionary.Category
import com.github.sergio.igwt.kfaker.dictionary.CategoryName
import com.github.sergio.igwt.kfaker.dictionary.Dictionary
import com.github.sergio.igwt.kfaker.dictionary.RawExpression
import com.github.sergio.igwt.kfaker.dictionary.getCategoryName
import com.github.sergio.igwt.kfaker.provider.FakeDataProvider
import java.io.File
import java.io.InputStream
import java.util.Locale
import java.util.regex.Matcher
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

internal class FakerService @JvmOverloads internal constructor(locale: Locale? = null) {
    private val randomService = RandomService()
    private val curlyBraceRegex = Regex("""#\{(\p{L}+\.)?(.*?)\}""")
    val dictionary = load(locale)

    internal constructor(locale: String) : this(Locale.forLanguageTag(locale))

    /**
     * Reads values of the default 'en' locale files into this [dictionary].
     *
     * Additionally `if (locale != null && locale.isValid)`, reads the contents of the specified locale file
     * into this [dictionary] (Will overwrite all matching keys with values from specified locale file.)
     *
     * @throws IllegalArgumentException if the [locale] is invalid or locale dictionary file is not present on the classpath.
     */
    private fun load(locale: Locale? = null): Dictionary {
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

        if (locale != null && locale.toString() != "en") {
            val localeFileStream = requireNotNull(getResourceAsStream("locales/$locale.yml")) {
                "Dictionary file not found for locale value: $locale"
            }

            defaultValues.putAll(readCategory(localeFileStream, locale))
        }

        val categories = defaultValues.entries.toList().map { Category(getCategoryName(it.key), it.value) }
        return Dictionary(categories)
    }

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
     * Returns [Category] instance by its [categoryName]
     */
    fun fetchCategory(categoryName: CategoryName): Category {
        return dictionary.categories.firstOrNull { it.categoryName == categoryName }
            ?: throw NoSuchElementException("Category with name '$categoryName' not found")
    }

    /**
     * Returns raw value as [String] from a given [category] fetched by its [key]
     */
    fun getRawValue(category: Category, key: String): RawExpression {
        val parameterValue = category.values[key]
            ?: throw NoSuchElementException("Parameter with name '$key' for this category not found")

        return when (parameterValue) {
            is List<*> -> RawExpression(randomService.randomValue(parameterValue) as String)
            is String -> RawExpression(parameterValue)
            is Map<*, *> -> {
                // TODO: 2/20/2019 this should probably be reimplemented
                when {
                    parameterValue.values.all { it is String } -> {
                        val values = parameterValue.values.toList()
                        RawExpression(randomService.randomValue(values) as String)
                    }
                    parameterValue.values.all { it is Map<*, *> } -> {
                        val values = parameterValue.values.toList()
                        RawExpression(randomService.randomValue(values.map { "$it" }))
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

    fun resolve(faker: Faker, category: Category, key: String): String {
        val rawExpression = getRawValue(category, key)
        return resolveExpression(faker, category, rawExpression)
    }

    fun resolve(faker: Faker, category: Category, key: String, secondaryKey: String): String {
        TODO("Not implemented")
    }

    private tailrec fun resolveExpression(faker: Faker, category: Category, rawExpression: RawExpression): String {
        val sb = StringBuffer()

        val resolvedExpression = when {
            curlyBraceRegex.containsMatchIn(rawExpression.value) -> {
                findMatchesAndAppendTail(rawExpression.value, sb, curlyBraceRegex) {
                    val simpleClassName = it.group(1)?.trimEnd('.')

                    val replacement = when (simpleClassName != null) {
                        true -> {
                            val providerType = getProvider(faker, simpleClassName)
                            val propertyName = providerType.getPropertyName(it.group(2))

                            providerType.callProperty(propertyName)
                        }
                        false -> getRawValue(category, it.group(2)).value
                    }

                    it.appendReplacement(sb, replacement)
                }
            }
            else -> rawExpression.value
        }

        return if (!curlyBraceRegex.containsMatchIn(resolvedExpression)) {
            resolvedExpression
        } else resolveExpression(faker, category, RawExpression(resolvedExpression))
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : FakeDataProvider> T.callProperty(kProperty1: KProperty1<out T, Any?>): String {
        return (kProperty1.call(this) as () -> String).invoke()
    }

    private fun <T : FakeDataProvider> T.getPropertyName(rawString: String): KProperty1<out T, Any?> {
        val propertyName = rawString.split("_").mapIndexed { i: Int, s: String ->
            if (i == 0) s else s.substring(0, 1).toUpperCase() + s.substring(1)
        }.joinToString("")

        return this::class.declaredMemberProperties.first { it.name == propertyName }
    }

    private fun getProvider(faker: Faker, simpleClassName: String): FakeDataProvider {
        val kProp = faker::class.declaredMemberProperties.first {
            it.name.toLowerCase() == simpleClassName.toLowerCase()
        }

        return kProp.call(faker) as FakeDataProvider
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

