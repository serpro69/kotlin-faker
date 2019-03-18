package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.ResourceLoader.getResource
import io.github.serpro69.kfaker.ResourceLoader.getResourceAsStream
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.*
import java.io.*
import java.io.File
import java.util.Locale
import java.util.regex.*
import kotlin.NoSuchElementException
import kotlin.collections.LinkedHashMap
import kotlin.collections.set
import kotlin.reflect.*
import kotlin.reflect.full.*

/**
 * Internal class used for resolving yaml expressions into values.
 *
 * @constructor creates an instance of this [FakerService] with the default 'en' locale if [locale] is not specified.
 */
internal class FakerService @JvmOverloads internal constructor(locale: Locale? = null) {
    private val randomService = RandomService()
    private val curlyBraceRegex = Regex("""#\{(\p{L}+\.)?(.*?)\}""")
    private val numericRegex = Regex("""(#+)[^\{\s+\p{L}+]?""")
    private val letterRegex = Regex("""(\?\?+)[^\{\s+\p{L}+]?""")
    val dictionary = load(locale)

    /**
     * @constructor creates an instance of this [FakerService] with the given [locale]
     */
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

    /**
     * Reads values from the [file] for the given [locale] and returns as [LinkedHashMap]
     * where `key` represents the category name, i.e. `address`,
     * and `value` represents the [Map] of values from this category.
     */
    @Suppress("SameParameterValue")
    private fun readCategory(file: File, locale: Locale): LinkedHashMap<String, Map<String, *>> {
        return readCategory(file.inputStream(), locale)
    }

    /**
     * Reads values from the [inputStream] for the given [locale] and returns as [LinkedHashMap]
     * where `key` represents the category name, i.e. `address`,
     * and `value` represents the [Map] of values from this category.
     */
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
     * Returns raw value as [RawExpression] from a given [category] fetched by its [key]
     */
    fun getRawValue(category: Category, key: String): RawExpression {
        val parameterValue = category.values[key]
            ?: throw NoSuchElementException("Parameter '$key' not found in '${category.categoryName.toLowerCase()}' category")

        return when (parameterValue) {
            is List<*> -> {
                when (val value = randomService.randomValue(parameterValue)) {
                    is List<*> -> RawExpression(randomService.randomValue(value) as String)
                    is String -> RawExpression(value)
                    is Int -> RawExpression(value.toString())
                    else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                }
            }
            is String -> RawExpression(parameterValue)
            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
        }
    }

    /**
     * Returns raw value as [RawExpression] from a given [category] fetched by its [key] and [secondaryKey]
     */
    fun getRawValue(category: Category, key: String, secondaryKey: String): RawExpression {
        val parameterValue = category.values[key]
            ?: throw NoSuchElementException("Parameter '$key' not found in '${category.categoryName.toLowerCase()}' category")

        return when (parameterValue) {
            is Map<*, *> -> {
                if (secondaryKey == "") {
                    val mapValues = parameterValue.values.toList()
                    when (val secondaryValue = randomService.randomValue(mapValues)) {
                        is List<*> -> RawExpression(randomService.randomValue(secondaryValue) as String)
                        is String -> RawExpression(secondaryValue)
                        is Map<*, *> -> RawExpression(secondaryValue.toString())
                        else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                    }
                } else {
                    parameterValue[secondaryKey]?.let { secondaryValue ->
                        when (secondaryValue) {
                            is List<*> -> RawExpression(randomService.randomValue(secondaryValue) as String)
                            is String -> RawExpression(secondaryValue)
                            is Map<*, *> -> RawExpression(secondaryValue.toString())
                            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                        }
                    } ?: throw NoSuchElementException("Secondary key '$secondaryKey' not found.")
                }
            }
            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
        }
    }

    /**
     * Returns raw value as [RawExpression] from a given [category] fetched by its [key], [secondaryKey], and [thirdKey]
     */
    fun getRawValue(category: Category, key: String, secondaryKey: String, thirdKey: String): RawExpression {
        val parameterValue = category.values[key]
            ?: throw NoSuchElementException("Parameter '$key' not found in '${category.categoryName.toLowerCase()}' category")

        return when (parameterValue) {
            is Map<*, *> -> {
                if (secondaryKey != "") {
                    parameterValue[secondaryKey]?.let { secondaryValue ->
                        when (secondaryValue) {
                            is Map<*, *> -> {
                                if (thirdKey == "") {
                                    val mapValues = secondaryValue.values.toList()
                                    when (val thirdValue = randomService.randomValue(mapValues)) {
                                        is List<*> -> RawExpression(randomService.randomValue(thirdValue) as String)
                                        is String -> RawExpression(thirdValue)
                                        else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                                    }
                                } else {
                                    secondaryValue[thirdKey]?.let { thirdValue ->
                                        when (thirdValue) {
                                            is List<*> -> RawExpression(randomService.randomValue(thirdValue) as String)
                                            is String -> RawExpression(thirdValue)
                                            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                                        }
                                    } ?: throw NoSuchElementException("Third key '$thirdKey' not found.")
                                }
                            }
                            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                        }
                    } ?: throw NoSuchElementException("Secondary key '$secondaryKey' not found.")
                } else {
                    throw IllegalArgumentException("Secondary key can not be empty string.")
                }
            }
            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
        }
    }

    // TODO: 3/18/2019 remove as it's unused
    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun resolveExpressionWithNumerals(rawValue: String): String {
        return rawValue.map { if (it == '#') randomService.nextInt(10) else it }.joinToString("")
    }

    /**
     * Resolves [RawExpression] value of the [key] in this [category].
     */
    fun resolve(faker: Faker, category: Category, key: String): String {
        val rawExpression = getRawValue(category, key)
        return resolveExpression(faker, category, rawExpression)
    }

    /**
     * Resolves [RawExpression] value of the [key] and [secondaryKey] in this [category].
     */
    fun resolve(faker: Faker, category: Category, key: String, secondaryKey: String): String {
        val rawExpression = getRawValue(category, key, secondaryKey)
        return resolveExpression(faker, category, rawExpression)
    }

    /**
     * Resolves [RawExpression] value of the [key], [secondaryKey], and [thirdKey] in this [category].
     */
    fun resolve(faker: Faker, category: Category, key: String, secondaryKey: String, thirdKey: String): String {
        val rawExpression = getRawValue(category, key, secondaryKey, thirdKey)
        return resolveExpression(faker, category, rawExpression)
    }

    /**
     * Resolves the [rawExpression] for this [category] and returns as [String].
     *
     * For yaml expressions:
     * - `#{city_prefix}` from `en: faker: address` would be resolved to getting value from `address: city_prefix`
     * - `#{Name.first_name} from `en: faker: address` would be resolved to calling [Name.name] function.
     * - `Apt. ###` returned from `en: faker: address: secondary_address` could be resolved to `Apt. 123`
     * where `123` could be a combination of any random digits.
     * - `???` from `en: faker: restaurant: name_prefix` could be resolved to `XYZ`
     * where `XYZ` could be a combination fo any random English letters in upper-case.
     *
     * Recursive expressions are also supported:
     * - `#{Name.name}` from `en: faker: book: author` that could be resolved to `#{first_name} #{last_name}` from `en: faker: name: name`
     * will be resolved to concatenating values from `en: faker: name: first_name` and `en: faker: name: last_name` and so on until
     * the expression is exhausted to the actual value.
     */
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
            letterRegex.containsMatchIn(rawExpression.value) -> rawExpression.value.letterify()
            else -> rawExpression.value
        }

        return if (!curlyBraceRegex.containsMatchIn(resolvedExpression) &&
            !numericRegex.containsMatchIn(resolvedExpression) &&
            !letterRegex.containsMatchIn(resolvedExpression)
        ) {
            resolvedExpression
        } else resolveExpression(faker, category, RawExpression(resolvedExpression))
    }


    /**
     * Replaces every `#` char for this [String] receiver with a random int from 0 to 9 inclusive
     * and returns the modified [String].
     */
    private fun String.numerify(): String {
        return this.map { if (it == '#') randomService.nextInt(10).toString() else "$it" }.joinToString("")
    }

    /**
     * Replaces every `?` char for this [String] receiver with a random upper-case letter from the English alphabet
     * and returns the modified [String].
     */
    private fun String.letterify(): String {
        return this.map {
            if (it == '?') randomService.nextLetter(upper = true).toString() else "$it"
        }.joinToString("")
    }

    /**
     * Calls the property of this [FakeDataProvider] receiver and returns the result as [String].
     *
     * @param T instance of [FakeDataProvider]
     * @param kProperty1 the [KProperty1] of [T]
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T : FakeDataProvider> T.callProperty(kProperty1: KProperty1<out T, Any?>): String {
        return (kProperty1.call(this) as () -> String).invoke()
    }

    /**
     * Gets the [KProperty1] of this [FakeDataProvider] receiver from the [rawString].
     *
     * Examples:
     *
     * Yaml expression in the form of `Name.first_name` would be translated to [Name.firstName] property.
     *
     * Yaml expression in the form of `Address.country` would be translated to [Address.country] property.
     *
     * @param T instance of [FakeDataProvider]
     */
    private fun <T : FakeDataProvider> T.getPropertyName(rawString: String): KProperty1<out T, Any?> {
        val propertyName = rawString.split("_").mapIndexed { i: Int, s: String ->
            if (i == 0) s else s.substring(0, 1).toUpperCase() + s.substring(1)
        }.joinToString("")

        return this::class.declaredMemberProperties.first { it.name == propertyName }
    }

    /**
     * Returns an instance of [FakeDataProvider] fetched by it's [simpleClassName] (case-insensitive).
     */
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

