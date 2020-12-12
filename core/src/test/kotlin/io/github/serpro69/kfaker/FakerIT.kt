package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.beUnique
import io.kotest.matchers.collections.containDuplicates
import io.kotest.matchers.collections.shouldNotContainAll
import io.kotest.matchers.collections.shouldNotContainExactly
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

@Suppress("UNCHECKED_CAST")
class FakerIT : DescribeSpec({
    describe("every public function in each provider is invoked without exceptions") {
        val faker = Faker()

        // Get a list of all publicly visible providers
        val providers: List<KProperty<*>> = faker::class.declaredMemberProperties.filter {
            it.visibility == KVisibility.PUBLIC && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
        }

        // Get a list of all publicly visible functions in each provider
        val providerFunctions = providers.associateBy { provider ->
            provider.getter.call(faker)!!::class.declaredMemberFunctions.filter {
                it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
            }
        }

        assertSoftly {
            providerFunctions.forEach { (functions, provider) ->
                functions.forEach {
                    context("result value for ${provider.name} ${it.name} is resolved correctly") {
                        val regex = Regex("""#\{.*}|#++""")

                        val value = when (it.parameters.size) {
                            1 -> it.call(provider.getter.call(faker)).toString()
                            2 -> it.call(provider.getter.call(faker), "").toString()
                            3 -> it.call(provider.getter.call(faker), "", "").toString()
                            else -> throw IllegalArgumentException("")
                        }

                        it("resolved value should not contain yaml expression") {
                            if (
                                !value.contains("#chuck and #norris")
                                && (provider.name != "invoice" && it.name != "pattern")
                                && (provider.name != "markdown" && it.name != "headers")
                            ) {
                                if (value.contains(regex)) {
                                    throw AssertionError("Value '$value' for '${provider.name} ${it.name}' should not contain regex '$regex'")
                                }
                            }
                        }

                        it("resolved value should not be empty string") {
                            if (value == "") {
                                throw AssertionError("Value for '${provider.name} ${it.name}' should not be empty string")
                            }
                        }

                        it("resolved value should not contain duplicates") {
                            val values = value.split(" ")

                            // Accounting for some exceptional cases where values are repeated
                            // in resolved expression
                            if (
                                value != "Tiger! Tiger!" // book#title
                                && (provider.name != "coffee" && it.name != "notes")
                                && (provider.name != "onePiece" && it.name != "akumasNoMi")
                                && value != "Girls Girls" // kPop#girlsGroups
                                && value != "Two Two" // kPop#firstGroups
                                && value != "woof woof" // creature#dog#sound
                                && (provider.name != "lorem" && it.name != "punctuation" && value != " ")
                                && value != "Duran Duran" // rockBand#name
                                && value != "Li Li"
                                && value != "Dee Dee"
                                && value != "Lola Lola" // cannabis#brands
                                && value != "Hail Hail" // pearlJam#songs
                                && value != "Help Help" // pearlJam#songs
                            ) {
                                // Since there's no way to modify assertion message in KotlinTest it's better to throw a custom error
                                if (values.odds() == values.evens()) {
                                    throw AssertionError("Value '$value' for '${provider.name} ${it.name}' should not contain duplicates")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    describe("Faker instance is initialized with default locale") {
        val faker = Faker()

        val defaultCountryUS = faker.address.defaultCountry()
        val peruOne = faker.address.countryByCode("PE")

        context("it is re-initialized with another locale value") {
            val config = FakerConfig.builder().create {
                locale = "nb-NO"
            }
            val otherFaker = Faker(config)

            it("matching keys should be overwritten in the re-initialized dictionary") {
                val defaultCountryNO = otherFaker.address.defaultCountry()

                assertSoftly {
                    defaultCountryNO shouldBe "Norge"
                    defaultCountryNO shouldNotBe defaultCountryUS
                }
            }

            it("non-matching default keys should be present in the re-initialized dictionary") {
                val peruTwo = otherFaker.address.countryByCode("PE")
                peruOne shouldBe peruTwo
            }
        }

/*        context("it is again re-initialized with default locale") {
            Faker.init()

            it("matching keys should be overwritten back to defaults") {
                val defaultCountry = Faker.address.defaultCountry()
                val peruThree = Faker.address.countryByCode("PE")

                assertSoftly {
                    defaultCountry shouldBe defaultCountryUS
                    peruThree shouldBe peruOne
                }
            }
        }*/
    }

    describe("Faker instance is initialized with custom locale") {
        val localeDir = requireNotNull(this::class.java.classLoader.getResource("locales/"))

        val locales = File(localeDir.toURI()).listFiles()?.mapNotNull {
            if (it.isFile && it.extension == "yml") {
                it.nameWithoutExtension
            } else null
        }

        it("Faker should be initialized without exceptions") {
            assertSoftly {
                locales?.forEach {
                    val config = FakerConfig.builder().create {
                        locale = it
                    }
                    assertDoesNotThrow { Faker(config) }
                }
            }
        }
    }

    // TODO this can be removed in 1.7.0 once deprecated functions are replaced
    context("configuration for unique generation of values") {
        val config = FakerConfig.builder().create { uniqueGeneratorRetryLimit = 100 }

        // repeat 30 times to make sure values are not included in the collection
        repeat(30) {
            val faker = Faker(config)
            faker.unique.configuration { enable(faker::address) }

            context("collection of unique values is generated #$it") {
                val countries = (0..5).map { faker.address.country() }

                val excludedCountries = listOf("Afghanistan", "Albania", "Algeria")
                faker.unique.configuration { exclude<Address>("country", excludedCountries) }
                val newCountries = (0..20).map { faker.address.country() }

                // FIXME this should probably be in a separate test
                val moreExcludedCountries = listOf("American Samoa", "Andorra", "Angola")
                faker.unique.configuration { exclude(Address::country, moreExcludedCountries) }
                val moreCountries = (0..20).map { faker.address.country() }
                // ---

                it("excluded values through config should not be included in the generation") {
                    assertSoftly {
                        newCountries shouldNotContainAll excludedCountries
                        moreCountries shouldNotContainAll moreExcludedCountries
                    }
                }

                it("already generated values through config should not be included in the generation") {
                    assertSoftly {
                        newCountries shouldNotContainAll countries
                        moreCountries shouldNotContainAll newCountries
                        moreCountries shouldNotContainAll countries
                    }
                }
            }
        }
    }

    describe("unique generation of values for category") {
        val config = FakerConfig.builder().create {
            uniqueGeneratorRetryLimit = 100
        }

        context("collection of values is generated") {
            val faker = Faker(config)

            faker.unique.enable(faker::address)
            faker.unique.enable(faker::ancient)

            val countries = (0..20).map { faker.address.country() }

            it("should not contain duplicates") {
                countries should beUnique()
            }

            context("used values are cleared") {
                faker.unique.clear(faker::address)

                val newCountries = (0..20).map { faker.address.country() }

                it("new collection should not contain duplicates") {
                    newCountries should beUnique()
                }

                it("new collection should not equal old collection") {
                    newCountries shouldNotBe countries
                }
            }
        }

        context("some values have been generated") {
            // repeat 30 times to make sure values are not included in the collection
            repeat(30) {
                val faker = Faker(config)
                faker.unique.enable(faker::address)

                val countries = (0..5).map { faker.address.country() }

                context("some values were marked for exclusion run#$it") {
                    val excludedCountries = listOf(
                        "Afghanistan",
                        "Albania",
                        "Algeria",
                        "American Samoa",
                        "Andorra",
                        "Angola"
                    )

                    faker.unique.exclude<Address>("country", excludedCountries)

                    val newCountries = (0..20).map { faker.address.country() }

                    it("excluded values should not be included in the generation") {
                        newCountries shouldNotContainAll excludedCountries
                    }

                    it("already generated values should not be included in the generation") {
                        newCountries shouldNotContainAll countries
                    }
                }

/*                context("exclude values with pattern") {
                    val faker = Faker(config)

                    // Exclude values for 'country' function
                    val excludedCountries = listOf(
                        "Afghanistan",
                        "Albania",
                        "Algeria",
                        "American Samoa",
                        "Andorra",
                        "Angola"
                    )

                    faker.unique.configuration {
                        // Enable unique generation for Address provider
                        enable(faker::address) {
                            excludePatterns(Address::country) { listOf(Regex("^B")) }
                            exclude(Address::country, excludedCountries)
                        }

                        // TODO not implemented
//                        // Applies to all enabled providers
//                        excludePatterns { listOf(Regex("^A")) }
//
//                        // Applies to all enabled providers
//                        val excludedValues = listOf("One", "Two", "Three")
//                        exclude(excludedValues)
                    }

                    // run some generation
                    val countries = (0..20).map { faker.address.country() }

*//*                    faker.unique.configuration {
                        enable(faker::address) {
                            val excludedCountries = listOf(
                                "Afghanistan",
                                "Albania",
                                "Algeria",
                                "American Samoa",
                                "Andorra",
                                "Angola"
                            )
                            exclude<Address>("country", excludedCountries)
                        }
                    }*//*

//                    // run more generation
//                    val newerCountries = (0..20).map { faker.address.country() }

//                    it("generated values should not match exclusion patterns") {
//                        countries.any { s -> s.startsWith("B") } shouldBe false
//                    }

                    it("excluded values should not be included in the generation") {
                        countries shouldNotContainAll excludedCountries
                    }
                }*/
            }
        }

        context("unique generation is not enabled for category") {
            val faker = Faker(config)

            context("some values were marked for exclusion") {
                val excludedCountries = listOf(
                    "Afghanistan",
                    "Albania",
                    "Algeria",
                    "American Samoa",
                    "Andorra",
                    "Angola",
                    "Anguilla",
                    "Antarctica",
                    "Antigua And Barbuda",
                    "Argentina",
                    "Armenia",
                    "Aruba",
                    "Australia",
                    "Austria",
                    "Aland Islands",
                    "Azerbaijan",
                    "Bahamas",
                    "Bahrain",
                    "Bangladesh",
                    "Barbados",
                    "Belarus",
                    "Belgium",
                    "Belize",
                    "Benin",
                    "Bermuda",
                    "Bhutan",
                    "Bolivia",
                    "Bonaire",
                    "Bosnia And Herzegovina",
                    "Botswana",
                    "Bouvet Island"
                )

                faker.unique.exclude<Address>("country", excludedCountries)

                val countries = (0..100).map { faker.address.country() }

                it("collection can have duplicates") {
                    countries should containDuplicates()
                }

                it("collection can have excluded values") {
                    countries.any { excludedCountries.contains(it) } shouldBe true
                }
            }
        }

        context("values are generated for another category that is not marked for unique generation") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)

            val animalNames = (0..100).map { faker.animal.name() }

            it("collection can have duplicates") {
                animalNames shouldNot beUnique()
            }
        }

        context("unique generation for category is disabled") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)
            (0..20).map { faker.address.country() }

            faker.unique.disable(faker::address)

            context("collection of values is generated") {
                val countries = (0..100).map { faker.address.country() }

                it("collection can have duplicates") {
                    countries shouldNot beUnique()
                }
            }
        }

        context("unique generation is disabled for all categories") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)
            faker.unique.enable(faker::ancient)

            faker.unique.disableAll()

            context("collections of values are generated") {
                val countries = (0..100).map { faker.address.country() }
                val gods = (0..100).map { faker.ancient.god() }

                it("collection can have duplicates") {
                    assertSoftly {
                        countries shouldNot beUnique()
                        gods shouldNot beUnique()
                    }
                }
            }

            context("unique generation for a category is re-enabled") {
                faker.unique.enable(faker::address)

                context("collection of values is generated") {
                    val countries = (0..20).map { faker.address.country() }

                    it("collection should not contain duplicates") {
                        countries should beUnique()
                    }
                }
            }
        }

        context("unique generation is cleared for all categories") {
            val faker = Faker(config)
            faker.unique.enable(faker::address)
            faker.unique.enable(faker::ancient)

            // Generate some values first
            (0..20).map { faker.address.country() }
            (0..20).map { faker.ancient.hero() }

            faker.unique.clearAll()

            context("collections of values are generated") {
                val countries = (0..20).map { faker.address.country() }
                val heroes = (0..20).map { faker.ancient.hero() }

                it("collections should be unique") {
                    assertSoftly {
                        countries should beUnique()
                        heroes should beUnique()
                    }
                }
            }
        }
    }

    describe("local unique generation") {
        val config = FakerConfig.builder().create {
            uniqueGeneratorRetryLimit = 100
        }
        val faker = Faker(config)

        context("unique property prefixes the category function invocation") {
            val countries = (0..20).map {
                faker.address.unique.country()
            }

            it("collection should not contain duplicates") {
                countries should beUnique()
            }

            it("other functions of the same category should not be marked as unique") {
                // This will produce an error if used with `unique` prefix
                // because there is only one value in the dictionary
                (0..10).map { faker.address.defaultCountry() }
            }

            context("values are cleared for the function name") {
                faker.address.unique.clear("country")

                val newCountries = (0..20).map {
                    faker.address.unique.country()
                }

                it("collection should not contain duplicates") {
                    newCountries should beUnique()
                }
            }

            context("values are cleared for all functions") {
                repeat(20) { faker.address.unique.country() }
                repeat(20) { faker.address.unique.state() }

                faker.address.unique.clearAll()

                it("re-generated collections should be unique") {
                    val newCountries = (0..20).map { faker.address.unique.country() }
                    val states = (0..20).map { faker.address.unique.state() }

                    assertSoftly {
                        newCountries should beUnique()
                        states should beUnique()
                    }
                }
            }
        }
    }
})

private fun List<String>.odds() = this.mapIndexedNotNull { index, s ->
    if (index % 2 == 0) s else null
}

private fun List<String>.evens() = this.mapIndexedNotNull { index, s ->
    if (index % 2 != 0) s else null
}
