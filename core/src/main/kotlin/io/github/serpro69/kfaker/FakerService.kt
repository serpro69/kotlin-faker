package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.CategoryName
import io.github.serpro69.kfaker.dictionary.Dictionary
import io.github.serpro69.kfaker.dictionary.RawExpression
import io.github.serpro69.kfaker.dictionary.getCategoryName
import io.github.serpro69.kfaker.dictionary.toLowerCase
import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.Name
import java.io.InputStream
import java.util.*
import java.util.regex.Matcher
import kotlin.collections.set
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties

/**
 * Internal class used for resolving yaml expressions into values.
 *
 * @constructor creates an instance of this [FakerService] with the default 'en' locale if is not specified.
 */
internal class FakerService @JvmOverloads internal constructor(
    internal val faker: Faker,
    locale: String = "en", // TODO remove, this isn't really needed here since we can get locale from faker.config
) {
    internal val randomService = RandomService(faker.config)

    @Suppress("RegExpRedundantEscape")
    private val curlyBraceRegex = Regex("""#\{(\p{L}+\.)?(.*?)\}""")
    val dictionary = load(locale.replace("_", "-"))

    /**
     * @constructor creates an instance of this [FakerService] with the given [locale]
     */
    internal constructor(faker: Faker, locale: Locale) : this(faker, locale.toLanguageTag())

    private fun getDefaultFileStreams(): List<InputStream> {
        val classLoader = this.javaClass.classLoader

        return defaultFileNames.map {
            requireNotNull(classLoader.getResourceAsStream("locales/en/${it}"))
        }
    }

    private fun getLocalizedFileStream(locale: String = "en"): InputStream? {
        val classLoader = this.javaClass.classLoader

        return classLoader.getResourceAsStream("locales/$locale.yml")
    }

    /**
     * Reads values of the default 'en' locale files into this [dictionary].
     *
     * Additionally, `if (locale != null && locale.isValid)`, reads the contents of the specified locale file
     * into this [dictionary] (Will overwrite all matching keys with values from specified locale file.)
     *
     * @throws IllegalArgumentException if the [locale] is invalid or locale dictionary file is not present on the classpath.
     */
    private fun load(locale: String): Dictionary {
        val defaultValues = LinkedHashMap<String, Map<String, *>>()

        getDefaultFileStreams().forEach {
            readCategory(it, "en").entries.forEach { category ->
                defaultValues[category.key]?.let { existing ->
                    defaultValues[category.key] = existing.plus(category.value)
                } ?: run { defaultValues[category.key] = category.value }
            }

//             todo Add `separator` category from `locales/en.yml` file
            val enYml = requireNotNull(getLocalizedFileStream("en"))

            readCategory(enYml, "en").entries.forEach { category ->
                defaultValues[category.key] = category.value
            }
        }

        /**
         * Merges [default] and [localized] categories (providers) and values, using localized value if present.
         *
         * Will also handle partially-localized categories, including partially-localized functions with secondary_keys,
         * for example:
         *
         * IF (en.category.function.secondary_key1 AND en.category.function.secondary_key2) IS PRESENT
         * AND <locale>.category.function.secondary_key1 IS PRESENT
         * AND en.category.function.another_secondary_key2 IS ABSENT
         * THEN RETURN <locale>.category.function.secondary_key1 AND en.category.function.secondary_key2
         *
         * Currently does not handle missing <locale>.category.function.secondary_key.third_key scenarios.
         */
        fun merge(default: HashMap<String, Map<String, *>>, localized: HashMap<String, Map<String, *>>) {
            localized.forEach { (k, localizedMap) ->
                default[k]?.let { enMap ->
                    /*
                     * This is a provider level access for default providers (enMap) and localized providers (localizedMap),
                     * WHERE mapKey IS provider_name: [address, name, games, etc]
                     * AND map[mapKey] (i.e. map["name") IS provider_functions: [name.first_name, name.last_name, etc]
                     *
                     * For example:
                     * enMap.key == en.faker.games // 'games' provider for 'en' locale
                     * localizedMap.key == de.faker.games // 'games' provider for 'de' locale
                     * enMap["games"] == { {...}, {...}, pokemon={names=[...],locations=[...],moves=[...]} }
                     * localizedMap["games"] == { pokemon={names=[...]} }
                     */
                    default[k] = enMap.mapValuesTo(linkedMapOf()) { (k, v) ->
                        /*
                         * This is provider_functions level access for default providers (enMap).
                         * The goal here is to find-and-replace any matching functions (v) for each provider (k).
                         * But since some functions may contain secondary_key the following is needed.
                         */
                        if (v is Map<*, *> && localizedMap.containsKey(k)) {
                            // check if function has a secondary_key that is used to resolve the values
                            // if true we assume that u[k] should also be a Map because the structure of dict files should match
                            // v IS en.faker.games.<secondary_key> (i.e pokemon)
                            v.plus(localizedMap[k] as Map<*, *>)
                        } else if (localizedMap.containsKey(k)) {
                            // check if the primary_key (function_name) matches with localized provider
                            // if v is not a map, but localized key matches, then use the values for that key
                            localizedMap[k]
                        } else {
                            // else just return the original value
                            v
                        }
                    }
                }
            }
        }

        if (locale != "en") {
            val (localeFileStream, localeString) = getLocalizedFileStream(locale)?.let { it to locale } ?: let {
                val localeLang = locale.substringBefore("-")

                val fileStream = getLocalizedFileStream(localeLang)
                    ?: throw IllegalArgumentException("Dictionary file not found for locale values: '$locale' or '$localeLang'")

                fileStream to localeLang
            }

            readCategory(localeFileStream, localeString).forEach { cat ->
                when (cat.key) {
                    // 'separator' is a bit of a special case so needs to be handled separately
                    "separator" -> defaultValues[cat.key] = cat.value
                    else -> merge(defaultValues, hashMapOf(cat.key to cat.value))
                }
            }
        }

        val categories = defaultValues.asSequence().map {
            val value = when (it.key) {
                "separator" -> mapOf("separator" to it.value)
                "currency_symbol" -> mapOf("currency_symbol" to it.value)
                else -> it.value
            }
            Category(getCategoryName(it.key), value)
        }
        return Dictionary(categories.toList())
    }

    /**
     * Reads values from the [inputStream] for the given [locale] and returns as [LinkedHashMap]
     * where `key` represents the category name, i.e. `address`,
     * and `value` represents the [Map] of values from this category.
     */
    @Suppress("UNCHECKED_CAST")
    private fun readCategory(inputStream: InputStream, locale: String): LinkedHashMap<String, Map<String, *>> {
        val localeValues = Mapper.readValue(inputStream, Map::class.java)[locale] as Map<*, *>
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

    /**
     * Resolves [RawExpression] value of the [key] in this [category].
     */
    fun resolve(category: Category, key: String): String {
        val rawExpression = getRawValue(category, key)
        return resolveExpression(category, rawExpression)
    }

    /**
     * Resolves [RawExpression] value of the [key] and [secondaryKey] in this [category].
     */
    fun resolve(category: Category, key: String, secondaryKey: String): String {
        val rawExpression = getRawValue(category, key, secondaryKey)
        return resolveExpression(category, rawExpression)
    }

    /**
     * Resolves [RawExpression] value of the [key], [secondaryKey], and [thirdKey] in this [category].
     */
    fun resolve(category: Category, key: String, secondaryKey: String, thirdKey: String): String {
        val rawExpression = getRawValue(category, key, secondaryKey, thirdKey)
        return resolveExpression(category, rawExpression)
    }

    /**
     * Resolves the [rawExpression] for this [category] and returns as [String].
     *
     * For yaml expressions:
     * - `#{city_prefix}` from `en: faker: address` would be resolved to getting value from `address: city_prefix`
     * - `#{Name.first_name} from `en: faker: address` would be resolved to calling [Name.name] function.
     *
     * Recursive expressions are also supported:
     * - `#{Name.name}` from `en: faker: book: author` that could be resolved to `#{first_name} #{last_name}` from `en: faker: name: name`
     * will be resolved to concatenating values from `en: faker: name: first_name` and `en: faker: name: last_name` and so on until
     * the expression is exhausted to the actual value.
     *
     *
     * *It is worth noting that `'#'` and `'?'` chars will not be numerified/letterified inside this function,
     * and will be returned as is. To further numerify/letterify the resolved expression, one must do that explicitly
     * where it is needed.*
     *
     * For example, if the resolved string `"???###"` needs to be further numerified/letterified to return `XYZ012`,
     * where `XYZ` is a combination of pseudo-randomly generated English letters in upper-case,
     * and `012` is a combination of pseudo-randomly generated digits, it could be done like so:
     * ```
     * val resolvedExpressionString: String = resolveExpression() // returns ???###
     * with(fakerService) {
     *     resolvedExpressionString.numerify().letterify()
     * }
     * ```
     *
     * For recursive expressions, this must be used for function calls within the same [category],
     * but can be omitted for calls to other [category]s.
     * For example:
     *
     * `address.yml`:
     * ```
     * en:
     *   faker:
     *     address:
     *       street_number: ###
     *       street_name: ???
     *       street: "#{street_number} #{street_name} street"
     * ```
     * `Address.kt`:
     * ```
     * fun street_number() = with(fakerService) { resolveExpression().numerify() }
     * fun street_name() = with(fakerService) { resolveExpression().letterify() }
     * // Explicitly numerify and letterify returned value, even though we are doing that above as well
     * // because the functions are in the same categry
     * fun street() = with(fakerService) { resolveExpression().numerify().letterify()
     * ```
     */
    private tailrec fun resolveExpression(category: Category, rawExpression: RawExpression): String {
        val sb = StringBuffer()

        val resolvedExpression = when {
            curlyBraceRegex.containsMatchIn(rawExpression.value) -> {
                findMatchesAndAppendTail(rawExpression.value, sb, curlyBraceRegex) {
                    val simpleClassName = it.group(1)?.trimEnd('.')

                    val replacement = when (simpleClassName != null) {
                        true -> {
                            val providerType = getProvider(simpleClassName)
                            val propertyName = providerType.getFunctionName(it.group(2))

                            providerType.callFunction(propertyName)
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
        } else resolveExpression(category, RawExpression(resolvedExpression))
    }

    /**
     * Replaces every `#` char for this [String] receiver with a random int from 0 to 9 inclusive
     * and returns the modified [String].
     */
    fun String.numerify(): String {
        return map { if (it == '#') randomService.nextInt(10).toString() else "$it" }
            .joinToString("")
    }

    /**
     * Replaces every `?` char for this [String] receiver with a random upper-case letter from the English alphabet
     * and returns the modified [String].
     */
    fun String.letterify(): String {
        return map { if (it == '?') randomService.nextLetter(upper = true).toString() else "$it" }
            .joinToString("")
    }

    /**
     * Calls the property of this [FakeDataProvider] receiver and returns the result as [String].
     *
     * @param T instance of [FakeDataProvider]
     * @param kFunction the [KFunction] of [T]
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T : FakeDataProvider> T.callFunction(kFunction: KFunction<*>): String {
        return kFunction.call(this) as String
    }

    /**
     * Gets the [KFunction] of this [FakeDataProvider] receiver from the [rawString].
     *
     * Examples:
     *
     * Yaml expression in the form of `Name.first_name` would be translated to [Name.firstName] function.
     *
     * Yaml expression in the form of `Address.country` would be translated to [Address.country] function.
     *
     * @param T instance of [FakeDataProvider]
     */
    private fun <T : FakeDataProvider> T.getFunctionName(rawString: String): KFunction<*> {
        val propertyName = rawString.split("_").mapIndexed { i: Int, s: String ->
            if (i == 0) s else s.substring(0, 1).uppercase() + s.substring(1)
        }.joinToString("")

        return this::class.declaredFunctions.first { it.name == propertyName }
    }

    /**
     * Returns an instance of [FakeDataProvider] fetched by it's [simpleClassName] (case-insensitive).
     */
    private fun getProvider(simpleClassName: String): FakeDataProvider {
        val kProp = faker::class.declaredMemberProperties.first {
            it.name.lowercase() == simpleClassName.lowercase()
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
