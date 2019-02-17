package com.github.sergio.igwt.kfaker

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

                dictionary.keys.toList() shouldBe dictionaryKeys
            }

            "THEN recurring keys should be appended" {
                assertSoftly {
                    dictionary["creature"]?.keys?.size!! shouldBeGreaterThan 1
                    dictionary["games"]?.keys?.size!! shouldBeGreaterThan 1
                }
            }
        }

        "WHEN it is set to custom value" - {
            val esDictionary = FakerService(Locale.forLanguageTag("es")).dictionary
            val defaultDictionary = FakerService().dictionary

            "THEN matching keys should be overwritten in the localized dictionary" {
                val esAddress = esDictionary["address"]
                val defaultAddress = defaultDictionary["address"]

                esAddress shouldNotBe defaultAddress
            }

            "THEN non-matching default keys should be present in the localized dictionary" {
                val esGames = esDictionary["games"]
                val defaultGames = defaultDictionary["games"]

                esGames shouldBe defaultGames
            }
        }

        "WHEN it is set with a String value" - {
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
            val category = fakerService.fetchCategory("address")

            "THEN category map should contain all its keys" {
                category.keys shouldContainAll listOf(
                    "city_prefix", "city_suffix", "country", "country_by_code", "country_by_name", "country_code",
                    "country_code_long", "building_number", "community_prefix", "community_suffix", "community",
                    "street_suffix", "secondary_address", "postcode", "postcode_by_state", "state", "state_abbr",
                    "time_zone", "city", "street_name", "street_address", "full_address", "default_country"
                )
            }
        }

        "WHEN fetching raw value from category" - {
            "AND value type is List" - {
                val category = fakerService.fetchCategory("address")
                val rawValue = fakerService.getRawValue(category, "city_prefix")
                val cityPrefixes = listOf("North", "East", "West", "South", "New", "Lake", "Port")

                "THEN a random raw value from the list is returned as String" {
                    cityPrefixes shouldContain rawValue
                }
            }

            "AND value type is String" - {
                val category = fakerService.fetchCategory("id_number")
                val rawValue = fakerService.getRawValue(category, "valid")
                val expectedValue = "#{IDNumber.ssn_valid}"

                "THEN the raw value is returned as String" {
                    rawValue shouldBe expectedValue
                }
            }

            "AND value type is Map" - {
                "AND values of the Map entries is String type" - {
                    val category = fakerService.fetchCategory("address")
                    val rawValue = fakerService.getRawValue(category, "postcode_by_state")
                    val rawValues = listOf(
                        "350##", "995##", "967##", "850##", "717##", "900##", "800##", "061##", "204##", "198##",
                        "322##", "301##", "967##", "832##", "600##", "463##", "510##", "666##", "404##", "701##",
                        "042##", "210##", "026##", "480##", "555##", "387##", "650##", "590##", "688##", "898##",
                        "036##", "076##", "880##", "122##", "288##", "586##", "444##", "730##", "979##", "186##",
                        "029##", "299##", "577##", "383##", "798##", "847##", "050##", "222##", "990##", "247##",
                        "549##", "831##"
                    )

                    "THEN value is returned as random value of Map<*, *> entries as String" {
                        rawValues shouldContain rawValue
                    }
                }

                "AND values of Map entries is Map type" - {
                    val category = fakerService.fetchCategory("bank")
                    val rawValue = fakerService.getRawValue(category, "iban_details")
                    "THEN value is returned as random value of Map<Map<*,*>> entries as String" {
                        assertSoftly {
                            rawValue shouldContain "length"
                            rawValue shouldContain "bban_pattern"
                        }
                    }
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
                val category = fakerService.fetchCategory("name")

                "AND expression matches the root category parameter" - {
                    val rawExpression = fakerService.getRawValue(category, "first_name")

                    "THEN expression is resolved to raw value of the pointer" {
                        val resolvedValue = fakerService.resolveExpression(category, rawExpression)
                        resolvedValue.first().isUpperCase() shouldBe true
                    }
                }

                "AND expression matches parameter from another category" - {
                    "THEN expression is resolved to raw value of another category" {
                        TODO("Providers are not implemented")
                    }
                }

                "AND expression is recursive" - {
                    val rawExpression = fakerService.getRawValue(category, "name")

                    "THEN expression is resolved recursively" {
                        val resolvedValue = fakerService.resolveExpression(category, rawExpression)

                        assertSoftly {
                            resolvedValue.split(" ") shouldHaveAtLeastSize 2
                            resolvedValue.split(" ") shouldHaveAtMostSize 3
                            resolvedValue shouldNotContain Regex("""#\{\p{all}+?\}""")
                        }
                    }
                }
            }
        }
    }
})
