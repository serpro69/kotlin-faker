package com.github.sergio.igwt.kfaker

import io.kotlintest.assertSoftly
import io.kotlintest.matchers.haveKeys
import io.kotlintest.matchers.numerics.shouldBeGreaterThan
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FreeSpec
import java.util.Locale

internal class FakerServiceTest : FreeSpec() {

    init {
        "GIVEN dictionary is loaded" - {

            "WHEN locale for the dictionary is set to default value" - {
                val dictionary = FakerService().dictionary

                "THEN it should contain all keys for 'en' locale" {
                    run {
                        dictionary should haveKeys(
                            "address",
                            "ancient",
                            "creature",
                            "app",
                            "appliance",
                            "aqua_teen_hunger_force",
                            "artist",
                            "back_to_the_future",
                            "bank",
                            "basketball",
                            "beer",
                            "bojack_horseman",
                            "book",
                            "bossa_nova",
                            "breaking_bad",
                            "buffy",
                            "business",
                            "cannabis",
                            "chuck_norris",
                            "code",
                            "coffee",
                            "coin",
                            "color",
                            "commerce",
                            "community",
                            "company",
                            "compass",
                            "construction",
                            "cosmere",
                            "crypto_coin",
                            "culture_series",
                            "currency",
                            "dc_comics",
                            "demographic",
                            "dessert",
                            "device",
                            "games",
                            "dragon_ball",
                            "dr_who",
                            "dumb_and_dumber",
                            "dune",
                            "educator",
                            "electrical_components",
                            "esport",
                            "family_guy",
                            "file",
                            "finance",
                            "food",
                            "football",
                            "the_fresh_prince_of_bel_air",
                            "friends",
                            "funny_name",
                            "game_of_thrones",
                            "gender",
                            "ghostbusters",
                            "grateful_dead",
                            "greek_philosophers",
                            "hacker",
                            "harry_potter",
                            "heroes",
                            "heroes_of_the_storm",
                            "hey_arnold",
                            "hipster",
                            "hitchhikers_guide_to_the_galaxy",
                            "hobbit",
                            "house",
                            "how_i_met_your_mother",
                            "id_number",
                            "industry_segments",
                            "internet",
                            "invoice",
                            "job",
                            "kpop",
                            "lebowski",
                            "lord_of_the_rings",
                            "lorem",
                            "lovecraft",
                            "markdown",
                            "marketing",
                            "measurement",
                            "michael_scott",
                            "military",
                            "movie",
                            "music",
                            "name",
                            "nation",
                            "nato_phonetic_alphabet",
                            "new_girl",
                            "one_piece",
                            "parks_and_rec",
                            "phish",
                            "phone_number",
                            "cell_phone",
                            "country_code",
                            "princess_bride",
                            "programming_language",
                            "quote",
                            "relationship",
                            "restaurant",
                            "rick_and_morty",
                            "rock_band",
                            "rupaul",
                            "science",
                            "seinfeld",
                            "shakespeare",
                            "silicon_valley",
                            "simpsons",
                            "slack_emoji",
                            "source",
                            "south_park",
                            "space",
                            "stargate",
                            "star_trek",
                            "star_wars",
                            "stranger_things",
                            "stripe",
                            "subscription",
                            "superhero",
                            "sword_art_online",
                            "team",
                            "the_expanse",
                            "the_it_crowd",
                            "the_thick_of_it",
                            "twin_peaks",
                            "umphreys_mcgee",
                            "university",
                            "vehicle",
                            "venture_bros",
                            "verbs",
                            "v_for_vendetta",
                            "world_cup",
                            "yoda"
                        )
                    }
                }

                "THEN recurring keys should be appended" {
                    assertSoftly {
                        dictionary["creature"]?.keys?.size!! shouldBeGreaterThan 1
                        dictionary["games"]?.keys?.size!! shouldBeGreaterThan 1
                    }
                }
            }

            "WHEN locale for the dictionary is set to custom value" - {
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

            "WHEN locale for the dictionary is set with a String value" - {
                "THEN localized dictionary should be loaded" {
                    val esDictionary = FakerService("es").dictionary
                    esDictionary shouldNotBe null
                }
            }

            "WHEN locale for the dictionary is set with invalid String value" - {
                "THEN an exception is thrown when loading the dictionary" {
                    val exception = shouldThrow<IllegalArgumentException> {
                        FakerService("pe").dictionary
                    }

                    exception.message shouldBe "Dictionary file not found for locale value: pe"
                }
            }
        }
    }
}
