package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategoryData
import io.github.serpro69.kfaker.dictionary.Dictionary
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveAtMostSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldHaveSameLengthAs
import io.kotest.matchers.string.shouldNotContain
import java.util.*

val random = Random()

internal class FakerServiceTest : DescribeSpec({
    describe("locale for the dictionary") {
        context("it is set to default value") {
            it("it should load the category for 'en' locale") {
                fakerService(YamlCategory.ADDRESS)
                    .dictionary
                    .getEntryByCategory("address").keys shouldContainExactly addressCategoryKeys
            }

            it("recurring keys should be appended") {
                val service = fakerService()
                assertSoftly {
                    listOf("animal", "bird", "cat", "dog", "horse").forEach {
                        service.load(YamlCategory.CREATURE, Category.ofName(it))
                    }
                    service.dictionary.getEntryByCategory("creature").keys shouldHaveSize 5

                    listOf("elder_scrolls", "fallout", "half_life", "super_mario", "witcher", "zelda").forEach {
                        service.load(YamlCategory.GAMES, Category.ofName(it))
                    }
                    service.dictionary.getEntryByCategory("games").keys shouldHaveSize 6
                }
            }
        }

        context("it is set to custom value") {
            it("matching keys should be overwritten in the localized dictionary") {
                val esAddress = fakerService("es", YamlCategory.ADDRESS).dictionary.getEntryByCategory("address")
                val defaultAddress = fakerService(YamlCategory.ADDRESS).dictionary.getEntryByCategory("address")
                esAddress shouldNotBe defaultAddress
            }

            it("non-matching default keys should be present in the localized dictionary") {
                val cats = listOf("elder_scrolls", "fallout", "half_life", "super_mario", "witcher", "zelda").map {
                    Category.ofName(it)
                }.toTypedArray()
                    val esGames = fakerService("es", YamlCategory.GAMES, *cats).dictionary.getEntryByCategory("games")
                val defaultGames = fakerService(YamlCategory.GAMES, *cats).dictionary.getEntryByCategory("games")
                esGames shouldBe defaultGames
            }

            it("partially localized functions with secondary_key should contain non-localized default values ") {
                val deCommerce = fakerService("de", YamlCategory.COMMERCE).dictionary.getEntryByCategory("commerce")
                val defaultCommerce = fakerService(YamlCategory.COMMERCE).dictionary.getEntryByCategory("commerce")
                assertSoftly {
                    deCommerce["department"] shouldNotBe defaultCommerce["department"] // localized function
                    deCommerce["product_name"] shouldNotBe defaultCommerce["product_name"] // localized function with secondary_key
                    deCommerce["promotion_code"] shouldBe defaultCommerce["promotion_code"] // not localized
                }
            }
        }

        context("it is set with a valid String value") {
            it("localized category should be loaded") {
                fakerService("es", YamlCategory.ADDRESS).dictionary shouldNotBe emptyMap<YamlCategory, Map<*, *>>()
            }
        }

        context("it is set with invalid String value") {
            it("an exception is thrown when loading the category") {
                val exception = shouldThrow<IllegalArgumentException> {
                    fakerService("pe", YamlCategory.ADDRESS)
                }

                exception.message shouldBe "Dictionary file not found for locale values: 'pe' or 'pe'"
            }
        }

        // TODO not supported since 1.11.0
        //  see: https://github.com/serpro69/kotlin-faker/issues/131
        //  Note: when implemented also add tests to check that existing `lang-COUNTRY` locale is actually loaded, e.g. `fr-CA`
//        context("it is set as `lang-COUNTRY` but dictionary file exists only for `lang`") {
//            val frFRDict = FakerService(Faker(), "fr-FR").dictionary
//
//            it("localized dictionary for `lang` should be loaded") {
//                frFRDict shouldNotBe null
//            }
//        }
//
//        context("it is set as `lang_COUNTRY` String") {
//            val frFRDict = FakerService(Faker(), "fr_FR").dictionary
//
//            it("it should be set as `lang-COUNTRY` String") {
//                frFRDict shouldNotBe null
//            }
//        }
    }

    describe("dictionary is loaded") {
        context("fetching yamlCategoryData by key") {
            val yamlCategoryData = fakerService(YamlCategory.ADDRESS).fetchEntry(YamlCategory.ADDRESS)

            it("yamlCategoryData map should contain all its keys") {
                yamlCategoryData.keys shouldContainAll addressCategoryKeys
            }
        }

        context("fetching raw value from yamlCategoryData") {
            context("value type is List") {
                val service = fakerService(YamlCategory.ADDRESS)
                val yamlCategoryData = service.fetchEntry(YamlCategory.ADDRESS)
                val rawValue = service.getRawValue(yamlCategoryData, "city_prefix")

                it("a random raw value from the list is returned as String") {
                    val cityPrefixes = listOf("North", "East", "West", "South", "New", "Lake", "Port")

                    cityPrefixes shouldContain rawValue.value
                }
            }

            context("value type is String") {
                val service = fakerService(YamlCategory.ID_NUMBER)
                val yamlCategoryData = service.fetchEntry(YamlCategory.ID_NUMBER)
                val rawValue = service.getRawValue(yamlCategoryData, "valid")
                val expectedValue = "#{IDNumber.ssn_valid}"

                it("the raw value is returned as String") {
                    rawValue.value shouldBe expectedValue
                }
            }
        }

        context("fetching raw value from yamlCategoryData using secondary key") {
            context("value type for primaryKey is Map<String, String>") {
                /*
                  en:
                    faker:
                      address:
                        country_by_code: # primaryKey
                          PE: Peru # primaryKey.value == secondaryKey:value pair
                          NO: Norway # primaryKey.value == secondaryKey:value pair
                 */
                val fakerService = fakerService(YamlCategory.ADDRESS)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.ADDRESS)

                val peru = fakerService.getRawValue(yamlCategoryData, "country_by_code", "PE")
                val norway = fakerService.getRawValue(yamlCategoryData, "country_by_code", "NO")

                it("value is returned using secondary key") {
                    assertSoftly {
                        peru.value shouldBe "Peru"
                        norway.value shouldBe "Norway"
                    }
                }
            }

            context("value type is Map<String, List>") {
                val fakerService = fakerService(YamlCategory.EDUCATOR)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.EDUCATOR)
                val tertiaryType = fakerService.getRawValue(yamlCategoryData, "tertiary", "university_type")

                it("value is returned using secondary key") {
                    val types = listOf("College", "University", "Technical College", "TAFE")

                    types shouldContain tertiaryType.value
                }
            }

            context("value type is Map<Map<String, String>>") {
                /*
                  en:
                    faker:
                      bank:
                        iban_details: # primaryKey
                          ua: # primaryKey.value == secondaryKey
                            length: 29 # secondaryKey.value
                            bban_pattern: '\d{25}' # secondaryKey.value
                 */
                val fakerService = fakerService(YamlCategory.BANK)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.BANK)
                val rawValue = fakerService.getRawValue(yamlCategoryData, "iban_details", "ad")

                it("value is returned as random value of Map<Map<*,*>> entries as String") {
                    assertSoftly {
                        rawValue.value shouldContain "length"
                        rawValue.value shouldContain "bban_pattern"
                    }
                }
            }

            context("secondary key is invalid string") {
                val fakerService = fakerService(YamlCategory.ADDRESS)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.ADDRESS)

                it("exception is thrown") {
                    shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(yamlCategoryData, "postcode_by_state", "invalid")
                    }
                }

                it("exceptions contains message") {
                    val message = shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(yamlCategoryData, "postcode_by_state", "invalid")
                    }.message

                    message shouldContain "Secondary key 'invalid' not found"
                }
            }

            context("secondary key is empty String") {
                val fakerService = fakerService(YamlCategory.ADDRESS)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.ADDRESS)
                val countryByCode = fakerService.getRawValue(yamlCategoryData, "postcode_by_state", "")

                it("random value is returned") {
                    val rawValues = listOf(
                        "350##", "995##", "967##", "850##", "717##", "900##", "800##", "061##", "204##", "198##",
                        "322##", "301##", "967##", "832##", "600##", "463##", "510##", "666##", "404##", "701##",
                        "042##", "210##", "026##", "480##", "555##", "387##", "650##", "590##", "688##", "898##",
                        "036##", "076##", "880##", "122##", "288##", "586##", "444##", "730##", "979##", "186##",
                        "029##", "299##", "577##", "383##", "798##", "847##", "050##", "222##", "990##", "247##",
                        "549##", "831##"
                    )

                    rawValues shouldContain countryByCode.value
                }
            }

            context("value type !is Map") {
                val fakerService = fakerService(YamlCategory.ADDRESS)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.ADDRESS)

                it("exception is thrown") {
                    shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(yamlCategoryData, "country", "country")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(yamlCategoryData, "country", "country")
                    }

                    exception.message shouldContain "Unsupported type of raw value"
                }
            }
        }

        context("fetching raw value from yamlCategoryData using third key") {
            context("value type is String") {
                context("value is returned using third key") {
                    // TODO: 3/12/2019 not currently used in any of the dictionary files
                }
            }

            context("value type is List") {
                val fakerService = fakerService(YamlCategory.EDUCATOR)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.EDUCATOR)
                val tertiaryDegreeType = fakerService.getRawValue(yamlCategoryData, "tertiary", "degree", "type")

                it("value is returned using secondary key") {
                    val values = listOf("Associate Degree in", "Bachelor of", "Master of")
                    values shouldContain tertiaryDegreeType.value
                }
            }

            context("secondary key is invalid string") {
                val fakerService = fakerService(YamlCategory.EDUCATOR)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.EDUCATOR)

                it("exception is thrown") {
                    shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(yamlCategoryData, "tertiary", "invalid", "type")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(yamlCategoryData, "tertiary", "invalid", "type")
                    }

                    exception.message shouldContain "Secondary key 'invalid' not found"
                }
            }

            context("third key is invalid string") {
                val fakerService = fakerService(YamlCategory.EDUCATOR)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.EDUCATOR)

                it("exception is thrown") {
                    shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(yamlCategoryData, "tertiary", "degree", "invalid")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(yamlCategoryData, "tertiary", "degree", "invalid")
                    }

                    exception.message shouldContain "Third key 'invalid' not found"
                }
            }

            context("secondary key is empty String") {
                val fakerService = fakerService(YamlCategory.EDUCATOR)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.EDUCATOR)

                it("exception is thrown") {
                    shouldThrow<IllegalArgumentException> {
                        fakerService.getRawValue(yamlCategoryData, "tertiary", "", "type")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<IllegalArgumentException> {
                        fakerService.getRawValue(yamlCategoryData, "tertiary", "", "type")
                    }

                    exception.message shouldContain "Secondary key can not be empty string"
                }
            }

            context("third key is empty String") {
                val fakerService = fakerService(YamlCategory.GAMES, Category.ofName("dota"))
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.GAMES)
                val quote = fakerService.getRawValue(yamlCategoryData, "dota", "alchemist", "")

                it("random value is returned") {
                    val quotes = listOf(
                        "Easy now, this stuff is explosive!",
                        "Better living through alchemy!",
                        "Tell the ogre you're sorry."
                    )

                    quotes shouldContain quote.value
                }
            }

            context("value type !is Map") {
                val fakerService = fakerService(YamlCategory.ADDRESS)
                val yamlCategoryData = fakerService.fetchEntry(YamlCategory.ADDRESS)

                it("exception is thrown") {
                    shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(yamlCategoryData, "country_by_code", "PE", "")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(yamlCategoryData, "country_by_code", "PE", "")
                    }

                    exception.message shouldContain "Unsupported type of raw value"
                }
            }
        }

        context("resolving raw expression") {
            context("expression contains only # chars") {
                val rawValue = "######"
                val resolvedValue = with(fakerService()) { rawValue.numerify() }

                it("all # chars are replaced with random digits") {
                    assertSoftly {
                        resolvedValue.all { it.isDigit() } shouldBe true
                        resolvedValue.all { it == resolvedValue[0] } shouldBe false
                        resolvedValue shouldNotContain "#"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }
            }

            context("expression contains single # char") {
                val rawValue = "#"
                val resolvedValue = with(fakerService()) { rawValue.numerify() }

                it("# char is replaced with random digit") {
                    assertSoftly {
                        resolvedValue.all { it.isDigit() } shouldBe true
                        resolvedValue shouldNotContain "#"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }
            }

            context("expression contains a String and '#' and '?' chars") {
                val rawValue = "Winter is coming ###???"
                val resolvedValue = with(fakerService()) { rawValue.numerify().letterify() }

                it("all '#' chars are replaced with random digits") {
                    assertSoftly {
                        resolvedValue.takeLast(6).take(3).all { it.isDigit() } shouldBe true
                        resolvedValue.takeLast(6).take(3).all { it == resolvedValue[0] } shouldBe false
                        resolvedValue shouldNotContain "#"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }

                it("all '?' chars are replaced with random letters") {
                    assertSoftly {
                        resolvedValue.takeLast(3).all { it.isLetter() } shouldBe true
                        resolvedValue.takeLast(3).all { it == resolvedValue[0] } shouldBe false
                        resolvedValue shouldNotContain "?"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }

                it("String value is kept intact") {
                    resolvedValue.dropLast(6) shouldBe rawValue.dropLast(6)
                }
            }

            context("expression matches the curly-brace-regex") {
                context("expression matches the root yamlCategoryData parameter") {
                    val fakerService = fakerService(YamlCategory.NAME)
                    val yamlCategoryData = fakerService.fetchEntry(YamlCategory.NAME)

                    it("expression is resolved to raw value of the pointer") {
                        val resolvedValue = fakerService.resolve(yamlCategoryData, "first_name")

                        assertSoftly {
                            resolvedValue.first().isUpperCase() shouldBe true
                            resolvedValue.split(" ") shouldHaveAtMostSize 1
                        }
                    }
                }

                context("expression matches parameter from another yamlCategoryData") {
                    val fakerService = fakerService(YamlCategory.BOOK)
                    val yamlCategoryData = fakerService.fetchEntry(YamlCategory.BOOK)

                    it("expression is resolved to raw value of another yamlCategoryData") {
                        val resolvedValue = fakerService.resolve(yamlCategoryData, "author")

                        assertSoftly {
                            resolvedValue shouldNotBe ""
                            resolvedValue shouldNotContain "#"
                            resolvedValue shouldNotContain Regex("""#\{\p{all}+?}""")
                            resolvedValue.split(" ") shouldHaveAtLeastSize 2
                            resolvedValue.split(" ") shouldHaveAtMostSize 3
                            resolvedValue.split(" ").all { it.first().isUpperCase() } shouldBe true
                        }
                    }
                }

                context("expression is recursive") {
                    val fakerService = fakerService(YamlCategory.NAME)
                    val yamlCategoryData = fakerService.fetchEntry(YamlCategory.NAME)

                    it("expression is resolved recursively") {
                        val resolvedValue = fakerService.resolve(yamlCategoryData, "name")

                        assertSoftly {
                            resolvedValue.split(" ") shouldHaveAtLeastSize 2
                            resolvedValue.split(" ") shouldHaveAtMostSize 3
                            resolvedValue shouldNotContain Regex("""#\{\p{all}+?}""")
                        }
                    }
                }

                context("expression is resolved by secondary key") {
                    val fakerService = fakerService(YamlCategory.ADDRESS)
                    val address = fakerService.fetchEntry(YamlCategory.ADDRESS)
                    val peru = fakerService.resolve(address, "country_by_code", "PE")
                    val norway = fakerService.resolve(address, "country_by_code", "NO")

                    it("expression is resolved using secondary key") {
                        assertSoftly {
                            peru shouldBe "Peru"
                            norway shouldBe "Norway"
                        }
                    }
                }

                context("expression calls are chained with a dot '.' char") {
                    val fakerService = fakerService(YamlCategory.EDUCATOR)
                    val educator = fakerService.fetchEntry(YamlCategory.EDUCATOR)
                    val degreeType = fakerService.resolve(educator, "degree")

                    it("is resolved by functionName") {
                        degreeType.split(" ").take(2).joinToString(" ") shouldBeIn listOf(
                            "Associate Degree",
                            "Bachelor of",
                            "Master of",
                        )
                    }
                }
            }
        }
    }

    describe("multi-file localized dictionary") {
        context("ja locale") {
            it("matching keys should be overwritten in the localized dictionary") {
                val jaCitySuffix = fakerService("ja", YamlCategory.ADDRESS)
                    .dictionary
                    .getEntryByCategory("address")["city_suffix"] as List<*>
                jaCitySuffix shouldContainExactly listOf("市", "区", "町", "村", "郡")
            }

            it("non-matching default keys should be present in the localized dictionary") {
                val jaApp = fakerService("ja", YamlCategory.APP)
                    .dictionary
                    .getEntryByCategory("app")
                val defaultApp = fakerService(YamlCategory.APP).dictionary.getEntryByCategory("app")
                jaApp shouldBe defaultApp
            }

            it("partially localized provider should contain default values") {
                val jaCommunityPrefix = fakerService("ja", YamlCategory.ADDRESS)
                    .dictionary
                    .getEntryByCategory("address")["community_prefix"] as List<*>
                jaCommunityPrefix shouldContainExactly listOf(
                    "Park", "Summer", "Autumn", "Paradise", "Eagle", "Pine", "Royal", "University", "Willow"
                )
            }

            it("partially localized functions with secondary_key should contain non-localized default values ") {
                val jaCat = fakerService("ja", YamlCategory.CREATURE, "cat")
                    .dictionary
                    .getEntryByCategory("creature")["cat"] as Map<*, *>
                val defaultCat = fakerService(YamlCategory.CREATURE, "cat")
                    .dictionary
                    .getEntryByCategory("creature")["cat"] as Map<*, *>

                jaCat["name"] shouldBe defaultCat["name"]
                jaCat["registry"] shouldBe defaultCat["registry"]
                jaCat["breed"] shouldNotBe defaultCat["breed"]
            }
        }
    }
})

/**
 * Gets [YamlCategoryData] by its [category] from this [Dictionary].
 */
private fun Dictionary.getEntryByCategory(category: YamlCategory): YamlCategoryData {
    return this[category] ?: throw NoSuchElementException("Category with name $category not found")
}

/**
 * Gets [YamlCategoryData] by its [name] from this [Dictionary].
 */
private fun Dictionary.getEntryByCategory(name: String): YamlCategoryData {
    return getEntryByCategory(category = YamlCategory.findByName(name))
}

private fun fakerService(category: YamlCategory, secondaryCategory: String): FakerService {
    return fakerService("en", category, Category.ofName(secondaryCategory))
}

private fun fakerService(category: YamlCategory, vararg secondaryCategory: Category): FakerService {
    return fakerService("en", category, *secondaryCategory)
}

private fun fakerService(locale: String, category: YamlCategory, secondaryCategory: String): FakerService {
    return fakerService(locale, category, Category.ofName(secondaryCategory))
}

private fun fakerService(locale: String, category: YamlCategory, vararg secondaryCategory: Category): FakerService {
    val f = faker {
        fakerConfig { this.locale = locale }
    }
    return FakerService(f).also { fs ->
        if (secondaryCategory.isNotEmpty()) {
            secondaryCategory.forEach { fs.load(category, it) }
        } else fs.load(category)
    }
}

private fun fakerService(locale: String = "en"): FakerService {
    val f = faker {
        fakerConfig { this.locale = locale }
    }
    return FakerService(f)
}
