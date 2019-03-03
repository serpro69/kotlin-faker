package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.dictionary.CategoryName
import io.github.sergio.igwt.kfaker.dictionary.getCategoryByName
import io.github.sergio.igwt.kfaker.dictionary.toLowerCase
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldHaveAtLeastSize
import io.kotlintest.matchers.collections.shouldHaveAtMostSize
import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.matchers.string.shouldHaveSameLengthAs
import io.kotlintest.matchers.string.shouldNotContain
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import java.util.Locale

internal class FakerServiceTest : FreeSpec({
    "GIVEN locale for the dictionary" - {
        "WHEN it is set to default value" - {
            val dictionary = FakerService().dictionary

            "THEN it should contain all keys for 'en' locale" {
                val dictionaryKeys = listOf(
                    "address", "ancient", "creature", "app", "appliance", "aqua_teen_hunger_force", "artist",
                    "back_to_the_future", "bank", "basketball", "beer", "bojack_horseman", "book", "bossa_nova",
                    "breaking_bad", "buffy", "business", "cannabis", "chuck_norris", "code", "coffee", "coin", "color",
                    "commerce", "community", "company", "compass", "construction", "cosmere", "crypto_coin",
                    "culture_series", "currency", "dc_comics", "demographic", "dessert", "device", "games",
                    "dragon_ball", "dr_who", "dumb_and_dumber", "dune", "educator", "electrical_components", "esport",
                    "family_guy", "file", "finance", "food", "football", "the_fresh_prince_of_bel_air", "friends",
                    "funny_name", "game_of_thrones", "gender", "ghostbusters", "grateful_dead", "greek_philosophers",
                    "hacker", "harry_potter", "heroes", "heroes_of_the_storm", "hey_arnold", "hipster",
                    "hitchhikers_guide_to_the_galaxy", "hobbit", "house", "how_i_met_your_mother", "id_number",
                    "industry_segments", "internet", "invoice", "job", "kpop", "lebowski", "lord_of_the_rings", "lorem",
                    "lovecraft", "markdown", "marketing", "measurement", "michael_scott", "military", "movie", "music",
                    "name", "nation", "nato_phonetic_alphabet", "new_girl", "one_piece", "parks_and_rec", "phish",
                    "phone_number", "cell_phone", "country_code", "princess_bride", "programming_language", "quote",
                    "relationship", "restaurant", "rick_and_morty", "rock_band", "rupaul", "science", "seinfeld",
                    "shakespeare", "silicon_valley", "simpsons", "slack_emoji", "source", "south_park", "space",
                    "stargate", "star_trek", "star_wars", "stranger_things", "stripe", "subscription", "superhero",
                    "sword_art_online", "team", "the_expanse", "the_it_crowd", "the_thick_of_it", "twin_peaks",
                    "umphreys_mcgee", "university", "vehicle", "venture_bros", "verbs", "v_for_vendetta",
                    "world_cup", "yoda"
                )

                dictionary.categories.map { it.categoryName.toLowerCase() } shouldBe dictionaryKeys
            }

            "THEN recurring keys should be appended" {
                assertSoftly {
                    dictionary.getCategoryByName("creature").values.keys.size shouldBeGreaterThan 1
                    dictionary.getCategoryByName("games").values.keys.size shouldBeGreaterThan 1
                }
            }
        }

        "WHEN it is set to custom value" - {
            val esDictionary = FakerService(Locale.forLanguageTag("es")).dictionary
            val defaultDictionary = FakerService().dictionary

            "THEN matching keys should be overwritten in the localized dictionary" {
                val esAddress = esDictionary.getCategoryByName("address")
                val defaultAddress = defaultDictionary.getCategoryByName("address")

                esAddress shouldNotBe defaultAddress
            }

            "THEN non-matching default keys should be present in the localized dictionary" {
                val esGames = esDictionary.getCategoryByName("games")
                val defaultGames = defaultDictionary.getCategoryByName("games")

                esGames shouldBe defaultGames
            }
        }

        "WHEN it is set with a valid String value" - {
            "THEN localized dictionary should be loaded" {
                val esDictionary = FakerService("es").dictionary
                esDictionary shouldNotBe null
            }
        }

        "WHEN it is set with invalid String value" - {
            "THEN an exception is thrown when loading the dictionary" {
                val exception = shouldThrow<IllegalArgumentException> {
                    FakerService("pe").dictionary
                }

                exception.message shouldBe "Dictionary file not found for locale value: pe"
            }
        }
    }

    "GIVEN dictionary is loaded" - {
        val fakerService = FakerService()

        "WHEN fetching category by key" - {
            val category = fakerService.fetchCategory(CategoryName.ADDRESS)

            "THEN category map should contain all its keys" {
                val categoryKeys = listOf(
                    "city_prefix", "city_suffix", "country", "country_by_code", "country_by_name", "country_code",
                    "country_code_long", "building_number", "community_prefix", "community_suffix", "community",
                    "street_suffix", "secondary_address", "postcode", "postcode_by_state", "state", "state_abbr",
                    "time_zone", "city", "street_name", "street_address", "full_address", "default_country"
                )

                category.values.keys shouldContainAll categoryKeys
            }
        }

        "WHEN fetching raw value from category" - {
            "AND value type is List" - {
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)
                val rawValue = fakerService.getRawValue(category, "city_prefix")

                "THEN a random raw value from the list is returned as String" {
                    val cityPrefixes = listOf("North", "East", "West", "South", "New", "Lake", "Port")

                    cityPrefixes shouldContain rawValue.value
                }
            }

            "AND value type is String" - {
                val category = fakerService.fetchCategory(CategoryName.ID_NUMBER)
                val rawValue = fakerService.getRawValue(category, "valid")
                val expectedValue = "#{IDNumber.ssn_valid}"

                "THEN the raw value is returned as String" {
                    rawValue.value shouldBe expectedValue
                }
            }
        }

        "WHEN fetching raw value from category using secondary key" - {
            "AND value type is Map<String, String>" - {
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)

                val peru = fakerService.getRawValue(category, "country_by_code", "PE")
                val norway = fakerService.getRawValue(category, "country_by_code", "NO")

                "THEN value is returned using secondary key" {
                    assertSoftly {
                        peru.value shouldBe "Peru"
                        norway.value shouldBe "Norway"
                    }
                }
            }

            "AND value type is Map<Map<String, String>>" - {
                val category = fakerService.fetchCategory(CategoryName.BANK)
                val rawValue = fakerService.getRawValue(category, "iban_details", "ad")

                "THEN value is returned as random value of Map<Map<*,*>> entries as String" {
                    assertSoftly {
                        rawValue.value shouldContain "length"
                        rawValue.value shouldContain "bban_pattern"
                    }
                }
            }

            "AND secondary key is empty String" - {
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)
                val countryByCode = fakerService.getRawValue(category, "postcode_by_state", "")

                "THEN random value is returned" {
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

            "AND value type !is Map" - {
                val category = fakerService.fetchCategory(CategoryName.ADDRESS)

                "THEN exception is thrown" {
                    shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(category, "country", "country")
                    }
                }

                "THEN exception contains message" {
                    val exception = shouldThrow<UnsupportedOperationException> {
                        fakerService.getRawValue(category, "country", "country")
                    }

                    exception.message shouldContain "Unsupported type of raw value"
                }
            }
        }

        "WHEN resolving raw expression" - {
            "AND expression contains only # chars" - {
                val rawValue = "######"
                val resolvedValue = fakerService.resolveExpressionWithNumerals(rawValue)

                "THEN all # chars are replaced with random digits" {
                    assertSoftly {
                        resolvedValue.all { it.isDigit() } shouldBe true
                        resolvedValue.all { it == resolvedValue[0] } shouldBe false
                        resolvedValue shouldNotContain "#"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }
            }

            "AND expression contains a String and # chars" - {
                val rawValue = "Winter is coming ###"
                val resolvedValue = fakerService.resolveExpressionWithNumerals(rawValue)

                "THEN all # chars are replaced with random digits" {
                    assertSoftly {
                        resolvedValue.takeLast(3).all { it.isDigit() } shouldBe true
                        resolvedValue.takeLast(3).all { it == resolvedValue[0] } shouldBe false
                        resolvedValue shouldNotContain "#"
                        resolvedValue shouldHaveSameLengthAs rawValue
                    }
                }

                "THEN String value is kept intact" {
                    resolvedValue.dropLast(3) shouldBe rawValue.dropLast(3)
                }
            }

            "AND expression matches the curly-brace-regex" - {
                "AND expression matches the root category parameter" - {
                    val category = fakerService.fetchCategory(CategoryName.NAME)

                    "THEN expression is resolved to raw value of the pointer" {
                        val resolvedValue = fakerService.resolve(Faker, category, "first_name")

                        assertSoftly {
                            resolvedValue.first().isUpperCase() shouldBe true
                            resolvedValue.split(" ") shouldHaveAtMostSize 1
                        }
                    }
                }

                "AND expression matches parameter from another category" - {
                    val category = fakerService.fetchCategory(CategoryName.BOOK)

                    "THEN expression is resolved to raw value of another category" {
                        val resolvedValue = fakerService.resolve(Faker, category, "author")

                        assertSoftly {
                            resolvedValue shouldNotBe ""
                            resolvedValue shouldNotContain "#"
                            resolvedValue shouldNotContain Regex("""#\{\p{all}+?\}""")
                            resolvedValue.split(" ") shouldHaveAtLeastSize 2
                            resolvedValue.split(" ") shouldHaveAtMostSize 3
                            resolvedValue.split(" ").all { it.first().isUpperCase() } shouldBe true
                        }
                    }
                }

                "AND expression is recursive" - {
                    val category = fakerService.fetchCategory(CategoryName.NAME)

                    "THEN expression is resolved recursively" {
                        val resolvedValue = fakerService.resolve(Faker, category, "name")

                        assertSoftly {
                            resolvedValue.split(" ") shouldHaveAtLeastSize 2
                            resolvedValue.split(" ") shouldHaveAtMostSize 3
                            resolvedValue shouldNotContain Regex("""#\{\p{all}+?\}""")
                        }
                    }
                }

                "AND expression is resolved by secondary key" - {
                    val address = fakerService.fetchCategory(CategoryName.ADDRESS)
                    val peru = fakerService.resolve(Faker, address, "country_by_code", "PE")
                    val norway = fakerService.resolve(Faker, address, "country_by_code", "NO")

                    "THEN expression is resolved using secondary key" - {
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
