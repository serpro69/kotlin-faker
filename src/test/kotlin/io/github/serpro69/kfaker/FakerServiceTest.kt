package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.dictionary.*
import io.kotlintest.*
import io.kotlintest.matchers.collections.*
import io.kotlintest.matchers.numerics.*
import io.kotlintest.matchers.string.*
import io.kotlintest.specs.*
import java.util.*

val random = Random()

internal class FakerServiceTest : DescribeSpec({
    describe("locale for the dictionary") {
        context("it is set to default value") {
            val dictionary = FakerService(faker = Faker(), random = random).dictionary

            it("it should contain all keys for 'en' locale") {
                val dictionaryKeys = listOf(
                    "address", "ancient", "creature", "app", "appliance", "aqua_teen_hunger_force", "artist",
                    "back_to_the_future", "bank", "basketball", "beer", "bojack_horseman", "book", "bossa_nova",
                    "breaking_bad", "buffy", "business", "cannabis", "chuck_norris", "code", "coffee", "coin", "color",
                    "commerce", "community", "company", "compass", "construction", "cosmere", "crypto_coin",
                    "culture_series", "currency", "dc_comics", "demographic", "dessert", "device", "game", "games",
                    "dragon_ball", "dr_who", "dumb_and_dumber", "dune", "educator", "electrical_components", "esport",
                    "family_guy", "file", "finance", "food", "football", "the_fresh_prince_of_bel_air", "friends",
                    "funny_name", "game_of_thrones", "gender", "ghostbusters", "grateful_dead", "greek_philosophers",
                    "hacker", "harry_potter", "heroes", "heroes_of_the_storm", "hey_arnold", "hipster",
                    "hitchhikers_guide_to_the_galaxy", "hobbit", "house", "how_i_met_your_mother", "id_number",
                    "industry_segments", "internet", "invoice", "job", "kpop", "lebowski", "lord_of_the_rings", "lorem",
                    "lovecraft", "markdown", "marketing", "measurement", "michael_scott", "military", "movie", "music",
                    "name", "nation", "nato_phonetic_alphabet", "new_girl", "one_piece", "opera", "parks_and_rec",
                    "phish", "phone_number", "cell_phone", "country_code", "princess_bride", "programming_language",
                    "quote", "relationship", "restaurant", "rick_and_morty", "rock_band", "rupaul", "science",
                    "seinfeld", "shakespeare", "silicon_valley", "simpsons", "slack_emoji", "source", "south_park",
                    "space", "stargate", "star_trek", "star_wars", "stranger_things", "stripe", "subscription",
                    "superhero", "sword_art_online", "team", "the_expanse", "the_it_crowd", "the_thick_of_it",
                    "twin_peaks", "umphreys_mcgee", "university", "vehicle", "venture_bros", "verbs", "v_for_vendetta",
                    "world_cup", "yoda", "separator"
                )

                dictionary.categories.map { it.categoryName.toLowerCase() } shouldContainExactlyInAnyOrder dictionaryKeys
            }

            it("recurring keys should be appended") {
                assertSoftly {
                    dictionary.getCategoryByName("creature").values.keys.size shouldBeGreaterThan 1
                    dictionary.getCategoryByName("games").values.keys.size shouldBeGreaterThan 1
                }
            }
        }

        context("it is set to custom value") {
            val esDictionary = FakerService(Faker(), Locale.forLanguageTag("es"), random).dictionary
            val defaultDictionary = FakerService(faker = Faker(), random = random).dictionary

            it("matching keys should be overwritten in the localized dictionary") {
                val esAddress = esDictionary.getCategoryByName("address")
                val defaultAddress = defaultDictionary.getCategoryByName("address")

                esAddress shouldNotBe defaultAddress
            }

            it("non-matching default keys should be present in the localized dictionary") {
                val esGames = esDictionary.getCategoryByName("games")
                val defaultGames = defaultDictionary.getCategoryByName("games")

                esGames shouldBe defaultGames
            }
        }

        context("it is set with a valid String value") {
            it("localized dictionary should be loaded") {
                val esDictionary = FakerService(Faker(), "es", random).dictionary
                esDictionary shouldNotBe null
            }
        }

        context("it is set with invalid String value") {
            it("an exception is thrown when loading the dictionary") {
                val exception = shouldThrow<IllegalArgumentException> {
                    FakerService(Faker(), "pe", random).dictionary
                }

                exception.message shouldBe "Dictionary file not found for locale values: 'pe' or 'pe'"
            }
        }

        context("it is set as `lang-COUNTRY` but dictionary file exists only for `lang`") {
            val frFRDict = FakerService(Faker(), "fr-FR", random).dictionary

            it("localized dictionary for `lang` should be loaded") {
                frFRDict shouldNotBe null
            }
        }

        context("it is set as `lang_COUNTRY` String") {
            val frFRDict = FakerService(Faker(), "fr_FR", random).dictionary

            it("it should be set as `lang-COUNTRY` String") {
                frFRDict shouldNotBe null
            }
        }
    }

    describe("dictionary is loaded") {
        val fakerService = FakerService(faker = Faker(), random = random)

        context("fetching category by key") {
            val category = fakerService.fetchCategory(CategoryName.ADDRESS)

            it("category map should contain all its keys") {
                val categoryKeys = listOf(
                    "city_prefix", "city_suffix", "country", "country_by_code", "country_by_name", "country_code",
                    "country_code_long", "building_number", "community_prefix", "community_suffix", "community",
                    "street_suffix", "secondary_address", "postcode", "postcode_by_state", "state", "state_abbr",
                    "time_zone", "city", "street_name", "street_address", "full_address", "default_country"
                )

                category.values.keys shouldContainAll categoryKeys
            }
        }

        context("fetching raw value from category") {
            context("value type is List") {
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)
                val rawValue = fakerService.getRawValue(category, "city_prefix")

                it("a random raw value from the list is returned as String") {
                    val cityPrefixes = listOf("North", "East", "West", "South", "New", "Lake", "Port")

                    cityPrefixes shouldContain rawValue.value
                }
            }

            context("value type is String") {
                val category = fakerService.fetchCategory(CategoryName.ID_NUMBER)
                val rawValue = fakerService.getRawValue(category, "valid")
                val expectedValue = "#{IDNumber.ssn_valid}"

                it("the raw value is returned as String") {
                    rawValue.value shouldBe expectedValue
                }
            }
        }

        context("fetching raw value from category using secondary key") {
            context("value type for primaryKey is Map<String, String>") {
                /*
                  en:
                    faker:
                      address:
                        country_by_code: # primaryKey
                          PE: Peru # primaryKey.value == secondaryKey:value pair
                          NO: Norway # primaryKey.value == secondaryKey:value pair
                 */
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)

                val peru = fakerService.getRawValue(category, "country_by_code", "PE")
                val norway = fakerService.getRawValue(category, "country_by_code", "NO")

                it("value is returned using secondary key") {
                    assertSoftly {
                        peru.value shouldBe "Peru"
                        norway.value shouldBe "Norway"
                    }
                }
            }

            context("value type is Map<String, List>") {
                val category = fakerService.fetchCategory(CategoryName.EDUCATOR)
                val tertiaryType = fakerService.getRawValue(category, "tertiary", "type")

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
                val category = fakerService.fetchCategory(CategoryName.BANK)
                val rawValue = fakerService.getRawValue(category, "iban_details", "ad")

                it("value is returned as random value of Map<Map<*,*>> entries as String") {
                    assertSoftly {
                        rawValue.value shouldContain "length"
                        rawValue.value shouldContain "bban_pattern"
                    }
                }
            }

            context("secondary key is invalid string") {
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)

                it("exception is thrown") {
                    shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(category, "postcode_by_state", "invalid")
                    }
                }

                it("exceptions contains message") {
                    val message = shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(category, "postcode_by_state", "invalid")
                    }.message

                    message shouldContain "Secondary key 'invalid' not found"
                }
            }

            context("secondary key is empty String") {
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)
                val countryByCode = fakerService.getRawValue(category, "postcode_by_state", "")

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
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)

                it("exception is thrown") {
                    shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(category, "country", "country")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(category, "country", "country")
                    }

                    exception.message shouldContain "Unsupported type of raw value"
                }
            }
        }

        context("fetching raw value from category using third key") {
            context("value type is String") {
                context("value is returned using third key") {
                    // TODO: 3/12/2019 not currently used in any of the dictionary files
                }
            }

            context("value type is List") {
                val category = fakerService.fetchCategory(CategoryName.EDUCATOR)
                val tertiaryDegreeType = fakerService.getRawValue(category, "tertiary", "degree", "type")

                it("value is returned using secondary key") {
                    val values = listOf("Associate Degree in", "Bachelor of", "Master of")
                    values shouldContain tertiaryDegreeType.value
                }
            }

            context("secondary key is invalid string") {
                val category = fakerService.fetchCategory(CategoryName.EDUCATOR)

                it("exception is thrown") {
                    shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(category, "tertiary", "invalid", "type")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(category, "tertiary", "invalid", "type")
                    }

                    exception.message shouldContain "Secondary key 'invalid' not found"
                }
            }

            context("third key is invalid string") {
                val category = fakerService.fetchCategory(CategoryName.EDUCATOR)

                it("exception is thrown") {
                    shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(category, "tertiary", "degree", "invalid")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<NoSuchElementException> {
                        fakerService.getRawValue(category, "tertiary", "degree", "invalid")
                    }

                    exception.message shouldContain "Third key 'invalid' not found"
                }
            }

            context("secondary key is empty String") {
                val category = fakerService.fetchCategory(CategoryName.EDUCATOR)

                it("exception is thrown") {
                    shouldThrow<IllegalArgumentException> {
                        fakerService.getRawValue(category, "tertiary", "", "type")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<IllegalArgumentException> {
                        fakerService.getRawValue(category, "tertiary", "", "type")
                    }

                    exception.message shouldContain "Secondary key can not be empty string"
                }
            }

            context("third key is empty String") {
                val category = fakerService.fetchCategory(CategoryName.GAMES)
                val quote = fakerService.getRawValue(category, "dota", "alchemist", "")

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
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)

                it("exception is thrown") {
                    shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(category, "country_by_code", "PE", "")
                    }
                }

                it("exception contains message") {
                    val exception = shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(category, "country_by_code", "PE", "")
                    }

                    exception.message shouldContain "Unsupported type of raw value"
                }
            }
        }

        context("resolving raw expression") {
            context("expression contains only # chars") {
                val rawValue = "######"
                val resolvedValue = fakerService.resolveExpressionWithNumerals(rawValue)

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
                val resolvedValue = fakerService.resolveExpressionWithNumerals(rawValue)

                it("# char is replaced with random digit") {
                    assertSoftly {
                        resolvedValue.all { it.isDigit() } shouldBe true
                        resolvedValue shouldNotContain "#"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }
            }

            context("expression contains a String and # chars") {
                val rawValue = "Winter is coming ###"
                val resolvedValue = fakerService.resolveExpressionWithNumerals(rawValue)

                it("all # chars are replaced with random digits") {
                    assertSoftly {
                        resolvedValue.takeLast(3).all { it.isDigit() } shouldBe true
                        resolvedValue.takeLast(3).all { it == resolvedValue[0] } shouldBe false
                        resolvedValue shouldNotContain "#"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }

                it("String value is kept intact") {
                    resolvedValue.dropLast(3) shouldBe rawValue.dropLast(3)
                }
            }

            context("expression matches the curly-brace-regex") {
                context("expression matches the root category parameter") {
                    val category = fakerService.fetchCategory(CategoryName.NAME)

                    it("expression is resolved to raw value of the pointer") {
                        val resolvedValue = fakerService.resolve(category, "first_name")

                        assertSoftly {
                            resolvedValue.first().isUpperCase() shouldBe true
                            resolvedValue.split(" ") shouldHaveAtMostSize 1
                        }
                    }
                }

                context("expression matches parameter from another category") {

                    val category = fakerService.fetchCategory(CategoryName.BOOK)

                    it("expression is resolved to raw value of another category") {
                        val resolvedValue = fakerService.resolve(category, "author")

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
                    val category = fakerService.fetchCategory(CategoryName.NAME)

                    it("expression is resolved recursively") {
                        val resolvedValue = fakerService.resolve(category, "name")

                        assertSoftly {
                            resolvedValue.split(" ") shouldHaveAtLeastSize 2
                            resolvedValue.split(" ") shouldHaveAtMostSize 3
                            resolvedValue shouldNotContain Regex("""#\{\p{all}+?}""")
                        }
                    }
                }

                context("expression is resolved by secondary key") {
                    val address = fakerService.fetchCategory(CategoryName.ADDRESS)
                    val peru = fakerService.resolve(address, "country_by_code", "PE")
                    val norway = fakerService.resolve(address, "country_by_code", "NO")

                    context("expression is resolved using secondary key") {
                        assertSoftly {
                            peru shouldBe "Peru"
                            norway shouldBe "Norway"
                        }
                    }
                }
            }
        }
    }
})

