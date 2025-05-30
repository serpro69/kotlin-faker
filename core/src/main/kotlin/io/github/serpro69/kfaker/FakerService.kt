package io.github.serpro69.kfaker

import com.github.curiousoddman.rgxgen.RgxGen
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.Dictionary
import io.github.serpro69.kfaker.dictionary.RawExpression
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.dictionary.YamlCategory.CELL_PHONE
import io.github.serpro69.kfaker.dictionary.YamlCategory.CURRENCY_SYMBOL
import io.github.serpro69.kfaker.dictionary.YamlCategory.PHONE_NUMBER
import io.github.serpro69.kfaker.dictionary.YamlCategory.SEPARATOR
import io.github.serpro69.kfaker.dictionary.YamlCategoryData
import io.github.serpro69.kfaker.dictionary.lowercase
import io.github.serpro69.kfaker.exception.DictionaryKeyNotFoundException
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.Name
import java.io.InputStream
import java.util.*
import java.util.regex.Matcher
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.random.asJavaRandom

/**
 * Internal class used for resolving yaml expressions into values.
 *
 * @constructor creates an instance of this [FakerService] with the default 'en' locale if is not specified.
 */
class FakerService {
    @Suppress("RegExpRedundantEscape")
    private val locale: String
    internal val faker: AbstractFaker
    internal val randomService: RandomService
    internal val dictionary: Dictionary = EnumMap(YamlCategory::class.java)

    internal constructor(faker: AbstractFaker) {
        this.faker = faker
        this.locale = faker.config.locale.replace("_", "-")
        randomService = RandomService(faker.config)
    }

    /**
     * @constructor creates an instance of this [FakerService] with the given [locale]
     */
    internal constructor(faker: AbstractFaker, locale: Locale) {
        this.faker = faker
        this.locale = locale.toLanguageTag()
        randomService = RandomService(faker.config)
    }

    private fun getCategoryFileStream(
        locale: String,
        category: YamlCategory,
        secondaryCategory: Category?
    ): InputStream {
        return secondaryCategory?.let {
            requireNotNull(javaClass.classLoader.getResourceAsStream("locales/$locale/${it.lowercase()}.json"))
        } ?: requireNotNull(javaClass.classLoader.getResourceAsStream("locales/$locale/${category.lowercase()}.json"))
    }

    private fun getAllLocaleOrCategoryFileStreams(
        locale: String,
        category: YamlCategory,
        secondaryCategory: Category?,
    ): List<InputStream> {
        val baseLocale = locale.substringBefore("-")

        val requiredResources = when (locale) {
            "fr", "ja" -> emptyList()
            else -> listOf(
                requireNotNull(
                    javaClass.classLoader.getResourceAsStream("locales/$locale.json")
                        ?: javaClass.classLoader.getResourceAsStream("locales/$baseLocale.json")
                ) { "Dictionary file not found for locale values: '$locale' or '$baseLocale'" }
            )
        }

        val resourceFilenames = listOfNotNull(
            "locales/$baseLocale/${category.lowercase()}.json",
            secondaryCategory?.let { "locales/$baseLocale/${it.lowercase()}.json" },
            "locales/$locale/${category.lowercase()}.json",
            secondaryCategory?.let { "locales/$locale/${it.lowercase()}.json" },
        ).distinct()
        return requiredResources + resourceFilenames.mapNotNull { javaClass.classLoader.getResourceAsStream(it) }
    }

    private fun getCategoryFileStreamOrNull(
        locale: String,
        category: YamlCategory,
        secondaryCategory: Category?
    ): InputStream? {
        return secondaryCategory?.let {
            javaClass.classLoader.getResourceAsStream("locales/$locale/${it.lowercase()}.json")
        } ?: javaClass.classLoader.getResourceAsStream("locales/$locale/${category.lowercase()}.json")
    }

    private fun getLocaleFileStream(locale: String): InputStream? {
        return javaClass.classLoader.getResourceAsStream("locales/$locale.json")
            ?: javaClass.classLoader.getResourceAsStream("locales/${locale.substringBefore("-")}.json")
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
     * Currently, does not handle missing <locale>.category.function.secondary_key.third_key scenarios.
     */
    // TODO make internal and add tests
    private fun merge(
        default: HashMap<String, Map<String, *>>,
        localized: Map<String, Map<out String, *>>
    ): Map<String, Map<String, *>> {
        localized.forEach { (key, localizedMap) ->
            default[key]?.let { enMap ->
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
                default[key] = localizedMap.mapValuesTo(linkedMapOf()) { (k, v) ->
                    /*
                     * This is provider_functions level access for default providers (enMap).
                     * The goal here is to find-and-replace any matching functions (v) for each provider (k).
                     * But since some functions may contain secondary_key the following is needed.
                     */
                    if (v is Map<*, *> && enMap.containsKey(k)) {
                        // check if function has a secondary_key that is used to resolve the values
                        // if true we assume that u[k] should also be a Map because the structure of dict files should match
                        // v IS en.faker.games.<secondary_key> (i.e pokemon)
                        (enMap[k] as Map<*, *>).plus(v)
                    } else if (enMap.containsKey(k)) {
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
        return default
    }

    @Suppress("MemberVisibilityCanBePrivate")
    internal fun unload(category: YamlCategory, vararg secondaryCategory: Category): Dictionary {
        if (secondaryCategory.isNotEmpty()) {
            secondaryCategory.forEach { dictionary[category]?.remove(it.lowercase()) }
        } else dictionary.remove(category)
        return dictionary
    }

    @Suppress("unused")
    internal fun unloadAll(): Dictionary {
        YamlCategory.values().forEach { unload(it) }
        return dictionary
    }

    private fun computePhoneNumber(category: YamlCategory): HashMap<String, Any>? {
        return computePhoneNumber(category, "en")
    }

    private fun computePhoneNumber(category: YamlCategory, locale: String): HashMap<String, Any>? {
        val instr = when (locale) {
            "en", "ja", "fr" -> getCategoryFileStream(locale, PHONE_NUMBER, null)
            else -> getLocaleFileStream(locale)
        }
        return instr?.use {
            when (category) {
                PHONE_NUMBER, CELL_PHONE -> readCategoryOrNull(it, locale, category) as HashMap?
                else -> null
            }
        }
    }

    private fun computeSymbol(category: YamlCategory) = computeSymbol(category, "en") as Any

    private fun computeSymbol(category: YamlCategory, locale: String): Any? {
        val localeData = getLocaleFileStream(locale)?.use {
            Mapper.readValue(it, Map::class.java)[locale] as Map<*, *>
        } ?: requireNotNull(getLocaleFileStream("en")).use {
            Mapper.readValue(it, Map::class.java)["en"] as Map<*, *>
        }
        val fakerData = localeData["faker"] as Map<*, *>
        return fakerData[category.lowercase()]
    }

    /**
     * Reads values of the default 'en' locale files into this [dictionary].
     *
     * Additionally, `if (locale != null && locale.isValid)`, reads the contents of the specified locale file
     * into this [dictionary] (Will overwrite all matching keys with values from specified locale file.)
     *
     * @throws IllegalArgumentException if the [locale] is invalid or locale dictionary file is not present on the classpath.
     */
    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    fun load(category: YamlCategory, secondaryCategory: Category? = null): Dictionary {
        val defaultValues: LinkedHashMap<String, Any> = linkedMapOf()

        dictionary.compute(category) { _, categoryData -> // i.e. compute data for 'address' category
            // TODO can this be improved by doing smth along the lines of categoryData.computeIfAbsent()
            when (category) {
                /* Some keys are missing in localized phone.yml files,
                 * e.g. compare locales/en/phone.yml with locales/fr/phone.yml or locales/ja/phone.yml.
                 * So we merge the 'en/phone' keys with localized ones
                 * to avoid "Parameter '...' not found in 'phone_number' category" exceptions
                 *
                 * NB! We also needed to split-out handling of CELL_PHONE category into own branch
                 * because otherwise it would get merged with PHONE_NUMBER, and we would get
                 * "Parameter '...' not found in 'cell_phone' category" exceptions
                 */
                PHONE_NUMBER -> {
                    computePhoneNumber(category, locale)?.let { localePhones ->
                        computePhoneNumber(category)?.let { enPhones ->
                            enPhones.forEach { (k, v) -> localePhones.computeIfAbsent(k) { v } }
                        }
                        defaultValues.putAll(localePhones)
                    }
                }
                CELL_PHONE -> {
                    computePhoneNumber(category, locale)?.let { defaultValues.putAll(it) }
                        ?: computePhoneNumber(category)?.let { defaultValues.putAll(it) }
                }
                SEPARATOR, CURRENCY_SYMBOL -> {
                    computeSymbol(category, locale)?.let {
                        defaultValues[category.lowercase()] = it
                    } ?: run { defaultValues[category.lowercase()] = computeSymbol(category) }
                }
                else -> {
                    // get 'en' values first
                    getCategoryFileStream("en", category, secondaryCategory).use { instr ->
                        defaultValues.putAll(readCategory(instr, "en", category))
                    }

                    if (locale != "en") { // 'en' is already processed at this point
                        val inputs = getAllLocaleOrCategoryFileStreams(locale, category, secondaryCategory)

                        inputs.forEach { input ->
                            input.use { instr ->
                                readCategoryOrNull(instr, locale, category)?.let {
                                    val localized = with(category.lowercase()) {
                                        val merged = merge(
                                            hashMapOf(this to defaultValues),
                                            hashMapOf(this to it)
                                        )
                                        merged[this]
                                    }
                                    localized?.forEach { m -> defaultValues.merge(m.key, m.value as Any) { orig, loc -> loc } }
                                }
                            }
                        }
                    }
                }
            }
            categoryData?.let {
                defaultValues.forEach { (k, v) -> it.merge(k, v) { _, b -> b } }
                it
            } ?: defaultValues
        }
        return dictionary
    }

    /**
     * Reads values from the [inputStream] for the given [locale] and returns as [YamlCategoryData]
     * where each `key` represents a function in the data category, i.e. `address.country`,
     * and `value` represents values (of unknown type) from this function.
     *
     * Given the following yaml data:
     * ```
     *  uk: # locale
     *    faker: # dictionary -> Dictionary -> EnumMap<YamlCategory, YamlCategoryData>
     *      address: # category -> YamlCategoryData -> Map<String, Any>
     *        country: [Австралія, Австрія, Азербайджан] # data function
     *        building_number: ['#', '##', '1##']
     * ```
     *
     * The returned YamlCategoryData instance would equal to the following Map:
     * ```
     * {country=[Австралія, Австрія, Азербайджан], building_number: ['#', '##', '1##']}
     * ```
     */
    @Suppress("UNCHECKED_CAST", "SameParameterValue")
    private fun readCategory(inputStream: InputStream, locale: String, category: YamlCategory): YamlCategoryData {
        val localeData = Mapper.readValue(inputStream, Map::class.java)[locale] as Map<*, *>
        val fakerData = localeData["faker"] as LinkedHashMap<String, Map<String, *>>
        return fakerData[category.lowercase()] as LinkedHashMap<String, Any>
    }

    @Suppress("UNCHECKED_CAST")
    private fun readCategoryOrNull(
        inputStream: InputStream,
        locale: String,
        category: YamlCategory
    ): Map<String, Any>? {
        val localeData: Map<*, *> = with(Mapper.readValue(inputStream, Map::class.java)) {
            (get(locale) ?: get(locale.substringBefore("-"))) as Map<*, *>
        }
        val fakerData = localeData["faker"] as LinkedHashMap<String, Map<String, *>>
        return fakerData[category.lowercase()] as Map<String, Any>?
    }

    fun getRawValue(category: YamlCategory, vararg keys: String): RawExpression {
        return when (keys.size) {
            1 -> getRawValue(category, keys.first())
            2 -> getRawValue(category, keys.first(), keys.last())
            3 -> getRawValue(category, keys[0], keys[1], keys[2])
            else -> throw UnsupportedOperationException("Unsupported keys length of ${keys.size}")
        }
    }

    /**
     * Returns raw value as [RawExpression] from a given [category] fetched by its [key]
     *
     * @throws DictionaryKeyNotFoundException IF the [dictionary] [category] does not contain the [key]
     */
    fun getRawValue(category: YamlCategory, key: String): RawExpression {
        val paramValue = getProviderData(category)[key]
            ?: throw DictionaryKeyNotFoundException("Parameter '$key' not found in '$category' category")

        return when (paramValue) {
            is List<*> -> {
                if (paramValue.isEmpty()) RawExpression("") else when (val value =
                    randomService.randomValue(paramValue)) {
                    is List<*> -> {
                        if (value.isEmpty()) RawExpression("") else RawExpression(randomService.randomValue(value) as String)
                    }
                    is String -> RawExpression(value)
                    is Int -> RawExpression(value.toString())
                    else -> throw UnsupportedOperationException("Unsupported type of raw value: ${paramValue::class.simpleName}")
                }
            }
            is String -> RawExpression(paramValue)
            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${paramValue::class.simpleName}")
        }
    }

    /**
     * Returns raw value as [RawExpression] from a given [category] fetched by its [key] and [secondaryKey]
     *
     * @throws DictionaryKeyNotFoundException IF the [dictionary] [category] does not contain the [key],
     * OR the primary [key] does not contain the [secondaryKey]
     */
    fun getRawValue(category: YamlCategory, key: String, secondaryKey: String): RawExpression {
        val parameterValue = getProviderData(category)[key]
            ?: throw DictionaryKeyNotFoundException("Parameter '$key' not found in '$category' category")

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
                    } ?: throw DictionaryKeyNotFoundException("Secondary key '$secondaryKey' not found.")
                }
            }
            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
        }
    }

    /**
     * Returns raw value as [RawExpression] for a given [category] fetched from the [dictionary] by its [key], [secondaryKey], and [thirdKey].
     *
     * @throws DictionaryKeyNotFoundException IF the [dictionary] [category] does not contain the [key],
     * OR the primary [key] does not contain the [secondaryKey],
     * OR the [secondaryKey] does not contain the [thirdKey]
     */
    fun getRawValue(
        category: YamlCategory,
        key: String,
        secondaryKey: String,
        thirdKey: String,
    ): RawExpression {
        val parameterValue = getProviderData(category)[key]
            ?: throw DictionaryKeyNotFoundException("Parameter '$key' not found in '$category' category")

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
                                    } ?: throw DictionaryKeyNotFoundException("Third key '$thirdKey' not found.")
                                }
                            }
                            else -> throw UnsupportedOperationException("Unsupported type of raw value: ${parameterValue::class.simpleName}")
                        }
                    } ?: throw DictionaryKeyNotFoundException("Secondary key '$secondaryKey' not found.")
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
    fun resolve(category: YamlCategory, key: String): String {
        val rawExpression = getRawValue(category, key)
        return resolveExpression(category, rawExpression)
    }

    /**
     * Resolves [RawExpression] value of the [key] and [secondaryKey] in this [category].
     */
    fun resolve(category: YamlCategory, key: String, secondaryKey: String): String {
        val rawExpression = getRawValue(category, key, secondaryKey)
        return resolveExpression(category, rawExpression)
    }

    /**
     * Resolves [RawExpression] value of the [key], [secondaryKey], and [thirdKey] in this [category].
     */
    fun resolve(category: YamlCategory, key: String, secondaryKey: String, thirdKey: String): String {
        val rawExpression = getRawValue(category, key, secondaryKey, thirdKey)
        return resolveExpression(category, rawExpression)
    }

    /**
     * Resolves the [rawExpression] for this [category] and returns as [String].
     *
     * For yaml expressions:
     * - `#{city_prefix}` from `en: faker: address` would be resolved to getting value from `address: city_prefix`
     * - `#{Name.first_name} from `en: faker: address` would be resolved to calling [Name.name] function.
     * - `#{Educator.tertiary.degree.type}` from `en: faker: educator: degree` would be resolved to calling [Degree.type] function.
     *   In this case the chained call needs to be implemented as a "class->property" hierarchy,
     *   i.e. [Educator] class must declare a `tertiary` property of [Tertiary] type,
     *   which in turn must declare a `degree` property of [Degree] type, and so on.
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
     * // because the functions are in the same category
     * fun street() = with(fakerService) { resolveExpression().numerify().letterify()
     * ```
     */
    @Suppress("KDocUnresolvedReference")
    private tailrec fun resolveExpression(category: YamlCategory, rawExpression: RawExpression): String {
        val cc = category
            .lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val primary = category.names.toMutableSet().plus(cc).joinToString("|")
        val secondary = category.children.toMutableSet().joinToString("|")
        // https://regex101.com/r/KIvagc/1
        //                          #\{(?!\d)(?:(Creature|Games|(Bird|Cat|Dog))\.)?((?![A-Z]\p{L}*\.).*?)\}
        val lexpr = Regex("""#\{(?!\d)(?i:($primary|($secondary))\.)?((?![A-Z]\p{L}*\.).*?)\}""")
        // https://regex101.com/r/I8gG7M/1
        //                          #\{(?!\d)(?!(?i:Creature|(?i:Bird|Cat|Dog))\.)(?:([A-Z]\p{L}+())\.)?(.*?)\}
        val cexpr = Regex("""#\{(?!\d)(?!(?i:$primary|(?i:$secondary))\.)(?:([A-Z]\p{L}+())\.)?(.*?)\}""")
        val sb = StringBuffer()
        val pc: (Matcher) -> YamlCategory? = { it.group(1)?.let { c -> YamlCategory.findByName(c) } }
        val sc: (Matcher) -> Category? = { it.group(2)?.let { c -> Category.ofName(c.uppercase()) } }

        val resolvedExpression = when {
            lexpr.containsMatchIn(rawExpression.value) -> {
                findMatchesAndAppendTail(rawExpression.value, sb, lexpr) {
                    val args = sc(it)?.let { c -> "${c.name.lowercase()}.${it.group(3)}".split(".").toTypedArray() }
                        ?: it.group(3).split(".").toTypedArray()
                    val replacement = getRawValue(category, *args).value
                    it.appendReplacement(sb, replacement)
                }
            }
            else -> rawExpression.value
        }

        return when {
            !lexpr.containsMatchIn(resolvedExpression)
                && !cexpr.containsMatchIn(resolvedExpression) -> resolvedExpression
            else -> {
                if (lexpr.containsMatchIn(resolvedExpression)) {
                    resolveExpression(category, RawExpression(resolvedExpression))
                } else {
                    val cm = cexpr.toPattern().matcher(resolvedExpression)
                    when { // resolve expression from another category, rinse and repeat
                        cm.find() -> {
                            val cat = pc(cm) ?: category
                            resolveExpression(cat, RawExpression(resolvedExpression.replace(cc, "")))
                        }
                        else -> resolveExpression(category, RawExpression(resolvedExpression))
                    }
                }
            }
        }
    }

    /**
     * Replaces every `#` char for this [String] receiver with a random int from 0 to 9 inclusive
     * and returns the modified [String].
     */
    val String.numerify: () -> String
        get() = {
            map { if (it == '#') randomService.nextInt(10).toString() else "$it" }
                .joinToString("")
        }

    /**
     * Replaces every `?` char for this [String] receiver with a random letter from the English alphabet
     * and returns the modified [String].
     *
     * @param upper set to `true` or `false` to control the case of generated letters
     */
    @Suppress("KDocUnresolvedReference")
    val String.letterify: (upper: Boolean?) -> String
        get() = { upper ->
            map {
                if (it == '?') {
                    randomService.nextLetter(upper = upper ?: randomService.nextBoolean()).toString()
                } else "$it"
            }.joinToString("")
        }

    /**
     * Replaces every `?` char for this [String] receiver with a random upper-case letter from the English alphabet
     * and returns the modified [String].
     */
    fun String.letterify() = letterify(true)

    /**
     * Returns a random string generated from this [String] receiver pattern.
     */
    val String.regexify: () -> String
        get() = { RgxGen.parse(this).generate(faker.config.random.asJavaRandom()) }

    /**
     * Returns an instance of [FakeDataProvider] fetched by its [simpleClassName] (case-insensitive).
     *
     * The function will attempt a [FakeDataProvider] in this [faker]'s declared member properties.
     * Failing that, the core [Faker] implementation will be used to do the same.
     *
     * @throws NoSuchElementException if neither this [faker] nor the core [Faker] implementation
     * has declared a provider that matches the [simpleClassName] parameter.
     */
    private fun getProviderData(primary: YamlCategory, secondary: Category? = null): YamlCategoryData {
        return dictionary[primary]
            ?: secondary?.let { load(primary, secondary)[primary] }
            ?: load(primary)[primary]
            ?: throw NoSuchElementException("Category $primary not found in $this")
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
