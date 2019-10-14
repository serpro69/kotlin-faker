package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.*
import io.kotlintest.*
import io.kotlintest.matchers.collections.*
import io.kotlintest.specs.*
import java.io.File
import kotlin.reflect.*
import kotlin.reflect.full.*

@Suppress("UNCHECKED_CAST")
class FakerIT : FreeSpec({
    "GIVEN every public function in each provider is invoked without exceptions" - {
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
                    "WHEN result value for ${provider.name + it.name} is resolved correctly" - {
                        val regex = Regex("""#\{.*}|#++""")

                        val value = when (it.parameters.size) {
                            1 -> it.call(provider.getter.call(faker)).toString()
                            2 -> it.call(provider.getter.call(faker), "").toString()
                            3 -> it.call(provider.getter.call(faker), "", "").toString()
                            else -> throw IllegalArgumentException("")
                        }

                        "THEN resolved value should not contain yaml expression" {
                            if (
                                !value.contains("#chuck and #norris")
                                && (provider.name != "invoice" && it.name != "pattern")
                                && (provider.name != "markdown" && it.name != "headers")
                            ) {
                                if (value.contains(regex)) {
                                    throw AssertionError("Value '$value' for '${provider.name + it.name}' should not contain regex '$regex'")
                                }
                            }
                        }

                        "THEN resolved value should not be empty string" {
                            if (value == "") {
                                throw AssertionError("Value for '${provider.name + it.name}' should not be empty string")
                            }
                        }

                        "THEN resolved value should not contain duplicates" {
                            val values = value.split(" ")

                            fun List<String>.odds() = this.mapIndexedNotNull { index, s ->
                                if (index % 2 == 0) s else null
                            }

                            fun List<String>.evens() = this.mapIndexedNotNull { index, s ->
                                if (index % 2 != 0) s else null
                            }

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
                            ) {
                                // Since there's no way to modify assertion message in KotlinTest it's better to throw a custom error
                                if (values.odds() == values.evens()) {
                                    throw AssertionError("Value '$value' for '${provider.name + it.name}' should not contain duplicates")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    "GIVEN Faker instance is initialized with default locale" - {
        val faker = Faker()

        val defaultCountryUS = faker.address.defaultCountry()
        val peruOne = faker.address.countryByCode("PE")

        "WHEN it is re-initialized with another locale value" - {
            val config = FakerConfig.builder().create {
                locale = "nb-NO"
            }
            val otherFaker = Faker(config)

            "THEN matching keys should be overwritten in the re-initialized dictionary" {
                val defaultCountryNO = otherFaker.address.defaultCountry()

                assertSoftly {
                    defaultCountryNO shouldBe "Norge"
                    defaultCountryNO shouldNotBe defaultCountryUS
                }
            }

            "THEN non-matching default keys should be present in the re-initialized dictionary" {
                val peruTwo = otherFaker.address.countryByCode("PE")
                peruOne shouldBe peruTwo
            }
        }

/*        "WHEN it is again re-initialized with default locale" - {
            Faker.init()

            "THEN matching keys should be overwritten back to defaults" {
                val defaultCountry = Faker.address.defaultCountry()
                val peruThree = Faker.address.countryByCode("PE")

                assertSoftly {
                    defaultCountry shouldBe defaultCountryUS
                    peruThree shouldBe peruOne
                }
            }
        }*/
    }

    "GIVEN Faker instance is initialized with custom locale" - {
        val localeDir = requireNotNull(this::class.java.classLoader.getResource("locales/"))

        val locales = File(localeDir.toURI()).listFiles()?.mapNotNull {
            if (it.isFile && it.extension == "yml") {
                it.nameWithoutExtension
            } else null
        }

        "THEN Faker should be initialized without exceptions" {
            locales?.forEach {
                val config = FakerConfig.builder().create {
                    locale = it
                }
                Faker(config)
            }
        }
    }

    "GIVEN unique generation for category is enabled" - {
        val config = FakerConfig.builder().create {
            uniqueGeneratorRetryLimit = 100
        }
        val faker = Faker(config)
        faker.unique.enable(faker::address)

        "WHEN collection of values is generated" - {
            val countries = (0..20).map { faker.address.country() }

            "THEN collection should not contain duplicates" {
                countries should beUnique()
            }

            "AND used values are cleared" - {
                faker.unique.clear(faker::address)

                val newCountries = (0..20).map { faker.address.country() }

                "THEN new collection should not contain duplicates" {
                    newCountries should beUnique()
                }

                "AND new collection should not equal old collection" {
                    newCountries shouldNotBe countries
                }
            }
        }

        "WHEN unique generation for category is disabled" - {
            faker.unique.disable(faker::address)

            "AND collection of values is generated" - {
                val countries = (0..50).map { faker.address.country() }

                "THEN collection can have duplicates" {
                    countries shouldNot beUnique()
                }
            }
        }

        "WHEN unique property prefixes the category function invocation" - {
            faker.unique.disableAll() // TODO: 13.10.2019 test for this function

            val countries = (0..20).map {
                faker.address.unique.country()
            }

            "THEN collection should not contain duplicates" {
                countries should beUnique()
            }

            "AND other functions of the same category should not be marked as unique" {
                // This will produce an error if used with `unique` prefix
                // because there is only one value in the dictionary
                (0..10).map { faker.address.defaultCountry() }
            }
        }
    }
})
