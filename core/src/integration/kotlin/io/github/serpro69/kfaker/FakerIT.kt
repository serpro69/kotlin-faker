package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.Money
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
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
            it.visibility == KVisibility.PUBLIC
                && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
                && it.returnType.classifier != Money::class // Ignore Money provider as it's a special case
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
                                && value != "Visual J#" // programmingLanguage#name
                                && value != "Acoustic #1" // pearlJam#songs
                                && value != "I am downloading some NP# music." // michaelScott#quotes
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
                                && value != "Mr. Mr." // kPop#thirdGroups
                                && value != "Chitty Chitty Bang Bang" // show#adultMusical
                                && value != "etc. etc." // marketing#buzzwords
                                && value != "Ook Ook" // ventureBros#character
                                && value != "Mahi Mahi" // food#ingredients
                                && value != "Cous Cous" // food#ingredients
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
            val config = fakerConfig { locale = "nb-NO" }
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
                    val config = fakerConfig { locale = it }
                    assertDoesNotThrow { Faker(config) }
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
