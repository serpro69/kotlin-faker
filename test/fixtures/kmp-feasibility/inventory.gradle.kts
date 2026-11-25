import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import java.util.Locale
import org.yaml.snakeyaml.Yaml

buildscript {
    repositories { mavenCentral() }
    dependencies {
        // Match the production YAML loader and locale-data baseline.
        classpath("org.yaml:snakeyaml:2.2")
        classpath("com.ibm.icu:icu4j:77.1")
    }
}

tasks.register("inventoryInputs") {
    val dictionaries = file("../../../core/src/jvmMain/resources/locales")
    val reportDirectory = layout.buildDirectory.dir("inventory")
    inputs.dir(dictionaries)
    outputs.dir(reportDirectory)

    doLast {
        val files = dictionaries.walkTopDown()
            .filter { it.isFile && it.extension in setOf("yml", "yaml") }
            .sortedBy { it.relativeTo(dictionaries).invariantSeparatorsPath }.toList()
        check(files.isNotEmpty()) { "No dictionaries found in $dictionaries" }
        val locales = sortedSetOf<String>()
        val types = sortedMapOf<String, Int>()
        val unusual = mutableListOf<String>()
        val fileRows = mutableListOf("source\tbytes\tlogicalLocale\tparsedLocaleKey\tcategories")
        val patternRows = mutableListOf("source\tkey\tinput\tpatternAfterPreprocessing")
        var nestedArrays = 0
        var maxDepth = 0
        var expressionCount = 0

        fun cell(value: Any?) = value.toString()
            .replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n").replace("\r", "\\r")

        fun visit(value: Any?, path: String, depth: Int) {
            maxDepth = maxOf(maxDepth, depth)
            val type = when (value) {
                is Map<*, *> -> "map"
                is List<*> -> "list"
                null -> "null"
                else -> value.javaClass.simpleName
            }
            types[type] = (types[type] ?: 0) + 1
            when (value) {
                is Map<*, *> -> value.forEach { (key, child) ->
                    if (key !is String) unusual += "$path: non-string key ${cell(key)}"
                    visit(child, "$path.${cell(key)}", depth + 1)
                }
                is List<*> -> value.forEachIndexed { index, child ->
                    if (child is List<*>) nestedArrays++
                    visit(child, "$path[$index]", depth + 1)
                }
                is String -> if (value.contains("#{")) expressionCount++
                else -> unusual += "$path: $type=${cell(value)}"
            }
        }

        fun patterns(value: Any?, source: String, key: String, transform: (String) -> String?) {
            when (value) {
                is Map<*, *> -> value.forEach { (k, v) -> patterns(v, source, "$key.$k", transform) }
                is List<*> -> value.forEachIndexed { i, v -> patterns(v, source, "$key[$i]", transform) }
                is String -> transform(value)?.let { transformed ->
                    check(!transformed.contains("#{")) { "Unresolved regex expression: $source:$key" }
                    patternRows += listOf(source, key, value, transformed).joinToString("\t", transform = ::cell)
                }
                null -> Unit
                else -> error("Unexpected regex input at $source:$key: $value")
            }
        }

        files.forEach { source ->
            val relative = source.relativeTo(dictionaries).invariantSeparatorsPath
            val data = source.inputStream().use { Yaml().loadAs(it, Map::class.java) }
            visit(data, relative, 0)
            data.forEach { (parsedLocale, root) ->
                // Inventory the logical filename separately: SnakeYAML 1.1 resolves bare `no`
                // to Boolean false. Record this discrepancy; do not repair the authoring input.
                val locale = relative.substringBefore('/').removeSuffix(".yml").removeSuffix(".yaml")
                locales += locale
                val faker = (root as Map<*, *>)["faker"] as Map<*, *>
                fileRows += "$relative\t${source.length()}\t$locale\t$parsedLocale\t${faker.keys.joinToString(",") }"
                val address = faker["address"] as? Map<*, *>
                patterns(address?.get("postcode"), relative, "$locale.address.postcode") {
                    if ('#' in it) null else it
                }
                val bank = faker["bank"] as? Map<*, *>
                val ibans = bank?.get("iban_details") as? Map<*, *>
                ibans?.forEach { (country, details) ->
                    check(details is Map<*, *> && details.keys.last() == "bban_pattern") {
                        "Bank.ibanDetails depends on last map entry: $relative:$country"
                    }
                    patterns(details["bban_pattern"], relative, "$locale.bank.iban_details.$country") { it }
                }
                val finance = faker["finance"] as? Map<*, *>
                patterns(finance?.get("credit_card"), relative, "$locale.finance.credit_card") {
                    it.replace('#', '0')
                }
                val vehicle = faker["vehicle"] as? Map<*, *>
                patterns(vehicle?.get("license_plate_by_state"), relative, "$locale.vehicle.license_plate_by_state") {
                    it.replace('#', '0').replace('?', 'A')
                }
            }
        }

        val localeRows = mutableListOf("input\tjavaLanguageTag\tstandard\tauxiliary\tindex\tpunctuation\tsupplementaryRanges\tstringElements")
        (locales + setOf("eo", "fil-PH", "und", "zz-ZZ", "en_US")).sorted().forEach { tag ->
            val locale = Locale.forLanguageTag(tag)
            val data = LocaleData.getInstance(ULocale.forLocale(locale))
            val sets = listOf(LocaleData.ES_STANDARD, LocaleData.ES_AUXILIARY, LocaleData.ES_INDEX, LocaleData.ES_PUNCTUATION)
                .map { data.getExemplarSet(UnicodeSet.MIN_VALUE, it) }
            val sizes = sets.map { set -> set.ranges().sumOf { it.codepointEnd - it.codepoint + 1 } }
            val supplementary = sets.sumOf { set -> set.ranges().count { it.codepointEnd > 0xffff } }
            val strings = sets.sumOf { it.size() } - sizes.sum()
            localeRows += (listOf(tag, locale.toLanguageTag()) + sizes + listOf(supplementary, strings)).joinToString("\t")
        }

        val report = reportDirectory.get().asFile.also { it.mkdirs() }
        report.resolve("files.tsv").writeText(fileRows.joinToString("\n", postfix = "\n"))
        report.resolve("regex-inputs.tsv").writeText(patternRows.joinToString("\n", postfix = "\n"))
        report.resolve("scalar-exceptions.txt").writeText(unusual.joinToString("\n", postfix = "\n"))
        report.resolve("locale-characters.tsv").writeText(localeRows.joinToString("\n", postfix = "\n"))
        val summary = """
            files=${files.size}
            locales=${locales.size}: ${locales.joinToString(", ")}
            sourceBytes=${files.sumOf { it.length() }}
            englishBytes=${files.filter { it.relativeTo(dictionaries).invariantSeparatorsPath.startsWith("en/") }.sumOf { it.length() }}
            types=$types
            maxNodeDepth=$maxDepth
            nestedArrays=$nestedArrays
            expressionStrings=$expressionCount
            regexInputRows=${patternRows.size - 1}
            scalarExceptions=${unusual.size}
        """.trimIndent() + "\n"
        report.resolve("summary.txt").writeText(summary)
        println(summary)
        println("Full source/key inventory: $report")
    }
}
