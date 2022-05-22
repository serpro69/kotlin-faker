package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.Money
import io.github.serpro69.kfaker.provider.misc.StringProvider
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
                && it.returnType.classifier != StringProvider::class // Ignore String provider
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
                            2 -> {
                                if (it.parameters[1].isOptional) { // optional params are enum typed (see functions in Dune, Finance or Tron, for example)
                                    it.callBy(mapOf(it.parameters[0] to provider.getter.call(faker))).toString()
                                } else it.call(provider.getter.call(faker), "").toString()
                            }
                            3 -> it.call(provider.getter.call(faker), "", "").toString()
                            else -> throw IllegalArgumentException("")
                        }

                        it("resolved value should not contain yaml expression") {
                            if (
                                !value.contains("#chuck and #norris")
                                && (provider.name != "markdown" && it.name != "headers")
                                && value !in valuesWithHashKey
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
                                (provider.name != "coffee" && it.name != "notes")
                                && (provider.name != "onePiece" && it.name != "akumasNoMi")
                                && (provider.name != "lorem" && it.name != "punctuation" && value != " ")
                                && value !in duplicatedValues
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

        val locales = File(localeDir.toURI()).listFiles().mapNotNull {
            if ((it.isFile && it.extension == "yml") || (it.isDirectory && it.name != "en")) {
                it.nameWithoutExtension
            } else null
        }

        locales.forEach {
            it("Faker with locale '$it' should be initialized without exceptions") {
                assertDoesNotThrow {
                    faker { fakerConfig { locale = it } }
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

private val duplicatedValues = listOf(
    "Tiger! Tiger!", // book#title
    "Girls Girls", // kPop#girlsGroups
    "Two Two", // kPop#firstGroups
    "woof woof", // creature#dog#sound
    "Duran Duran", // rockBand#name
    "Li Li", // heroesOfTheStorm#heroes
    "Dee Dee", // theFreshPrinceOfBelAir#characters
    "Lola Lola", // cannabis#brands
    "Hail Hail", // pearlJam#songs
    "Help Help", // pearlJam#songs
    "Mr. Mr.", // kPop#thirdGroups
    "Chitty Chitty Bang Bang", // show#adultMusical
    "etc. etc.", // marketing#buzzwords
    "Ook Ook", // ventureBros#character
    "Mahi Mahi", // food#ingredients
    "Cous Cous", // food#ingredients
    "Boom Boom", // superMario#characters
    "Min Min", // superSmashBros#fighter
)

private val valuesWithHashKey = listOf(
    "Visual J#", // programmingLanguage#name
    "Acoustic #1", // pearlJam#songs
    "I am downloading some NP# music.", // michaelScott#quotes
    "Cooler #6", // dragonBall#planets
    "Cooler #98", // dragonBall#planets
    "Cooler #256", // dragonBall#planets
    "Frieza #17", // dragonBall#planets
    "Frieza #79", // dragonBall#planets
    "Frieza #448", // dragonBall#planets
    "tL&^J@24CVF=zP46Lxixk`_a#=o6c5", // device#serial
    "S#arp", // kPop#firstGroups
)
