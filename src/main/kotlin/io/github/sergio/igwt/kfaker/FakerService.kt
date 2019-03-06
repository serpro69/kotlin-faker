package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.ResourceLoader.getResource
import io.github.sergio.igwt.kfaker.ResourceLoader.getResourceAsStream
import io.github.sergio.igwt.kfaker.dictionary.Category
import io.github.sergio.igwt.kfaker.dictionary.CategoryName
import io.github.sergio.igwt.kfaker.dictionary.Dictionary
import io.github.sergio.igwt.kfaker.dictionary.RawExpression
import io.github.sergio.igwt.kfaker.dictionary.getCategoryName
import io.github.sergio.igwt.kfaker.provider.FakeDataProvider
import java.io.File
import java.io.InputStream
import java.util.Locale
import java.util.regex.Matcher
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

internal class FakerService @JvmOverloads internal constructor(locale: Locale? = null) {
    private val randomService = RandomService()
    private val curlyBraceRegex = Regex("""#\{(\p{L}+\.)?(.*?)\}""")
    private val numericRegex = Regex("""(#+)[^\{\s+\p{L}+]""")
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

        if (locale != null && locale.toLanguageTag() != "en") {
            val localeFileStream = requireNotNull(getResourceAsStream("locales/${locale.toLanguageTag()}.yml")) {
                "Dictionary file not found for locale value: $locale"
            }

            readCategory(localeFileStream, locale).forEach {
                defaultValues.merge(it.key, it.value) { t, u -> t.plus(u) }
            }
        }

        val categories = defaultValues.entries.toList().map {
            Category(getCategoryName(it.key), it.value)
        }
        return Dictionary(categories)
    }

    @Suppress("SameParameterValue")
    private fun readCategory(file: File, locale: Locale): LinkedHashMap<String, Map<String, *>> {
        return readCategory(file.inputStream(), locale)
    }

    @Suppress("UNCHECKED_CAST")
    private fun readCategory(inputStream: InputStream, locale: Locale): LinkedHashMap<String, Map<String, *>> {
        val localeValues = Mapper.readValue(inputStream, Map::class.java)[locale.toLanguageTag()] as Map<*, *>
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
            is List<*> -> {
                when (val value = randomService.randomValue(parameterValue)) {
                    is List<*> -> RawExpression(randomService.randomValue(value) as String)
                    is String -> RawExpression(value)
                    else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                }
            }
            is String -> RawExpression(parameterValue)
            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
        }
    }

    /**
     * Returns raw value as [String] from a given [category] fetched by its [key] and [secondaryKey]
     */
    fun getRawValue(category: Category, key: String, secondaryKey: String): RawExpression {
        val parameterValue = category.values[key]
            ?: throw NoSuchElementException("Parameter with name '$key' for this category not found")

        return when (parameterValue) {
            is Map<*, *> -> {
                if (secondaryKey == "") {
                    val mapValues = parameterValue.values.toList()
                    when (val value = randomService.randomValue(mapValues)) {
                        is List<*> -> RawExpression(randomService.randomValue(value) as String)
                        is String -> RawExpression(value)
                        is Map<*, *> -> RawExpression(value.toString())
                        else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                    }
                } else {
                    parameterValue[secondaryKey]?.let {
                        when (it) {
                            is List<*> -> RawExpression(randomService.randomValue(it) as String)
                            is String -> RawExpression(it)
                            is Map<*, *> -> RawExpression(it.toString())
                            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                        }
                    } ?: throw NoSuchElementException("Secondary key '$secondaryKey' not found.")
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
        val rawExpression = getRawValue(category, key, secondaryKey)
        return resolveExpression(faker, category, rawExpression)
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
            numericRegex.containsMatchIn(rawExpression.value) -> rawExpression.value.numerify()
            else -> rawExpression.value
        }

        return if (!curlyBraceRegex.containsMatchIn(resolvedExpression) &&
            !numericRegex.containsMatchIn(resolvedExpression)
        ) {
            resolvedExpression
        } else resolveExpression(faker, category, RawExpression(resolvedExpression))
    }

    private fun String.numerify(): String {
        return this.map { if (it == '#') randomService.nextInt(10).toString() else "$it" }.joinToString("")
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

