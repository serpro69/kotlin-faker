package io.github.serpro69.kfaker.test.helper

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.Money
import io.github.serpro69.kfaker.provider.misc.RandomProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerScope
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

fun DescribeSpec.`every public function in each provider is invoked without exceptions`(faker: AbstractFaker) {
    describe("every public function in each provider is invoked without exceptions") {
        // Get a list of all publicly visible providers
        val providers: List<KProperty<*>> = faker::class.declaredMemberProperties.filter {
            it.visibility == KVisibility.PUBLIC
                && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
                && it.returnType.classifier != Money::class // Ignore Money provider as it's a special case
                && it.returnType.classifier != StringProvider::class // Ignore String provider
                && it.returnType.classifier != RandomProvider::class // Ignore String provider
        }

        // Get a list of all publicly visible sub-providers in each provider
        val subProviders: List<Pair<FakeDataProvider, List<KProperty<*>>>> = providers.mapNotNull { p ->
            val provider = p.getter.call(faker)!! as FakeDataProvider
            val subs = provider::class.declaredMemberProperties.filter {
                it.visibility == KVisibility.PUBLIC
                    && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
                    && it.returnType.classifier as KClass<*> != provider::class // exclude 'unique' properties
            }
            if (subs.isNotEmpty()) provider to subs else null
        }

        // Get a list of all publicly visible functions in each provider
        val providerFunctions = providers.associateBy { provider ->
            provider.getter.call(faker)!!::class.declaredMemberFunctions.filter {
                it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
            }
        }

        // Get all publicly visible functions in each sub-provider
        val subProviderFunctions = subProviders.map { (provider, subs) ->
            provider to subs.associateBy { sub ->
                sub.getter.call(provider)!!::class.declaredMemberFunctions.filter {
                    it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
                }
            }
        }

        assertSoftly {
            providerFunctions.forEach { (functions, provider) ->
                test(provider, functions, faker)
            }

            subProviderFunctions.forEach { (provider, subs) ->
                subs.forEach { (functions, sub) -> test(sub, functions, provider) }
            }
        }
    }
}

private suspend fun DescribeSpecContainerScope.test(provider: KProperty<*>, functions: List<KFunction<*>>, vararg caller: Any?) = functions.forEach {
    context("result value for ${provider.name}#${it.name} is resolved correctly") {
        val regex = Regex("""#\{.*}|#++""")

        val value = when (it.parameters.size) {
            1 -> it.call(provider.getter.call(*caller)).toString()
            2 -> {
                if (it.parameters[1].isOptional) { // optional params are enum typed (see functions in Dune, Finance or Tron, for example)
                    it.callBy(mapOf(it.parameters[0] to provider.getter.call(*caller))).toString()
                } else it.call(provider.getter.call(*caller), "").toString()
            }
            3 -> {
                if (it.parameters[1].isOptional && it.parameters[2].isOptional) {
                    it.callBy(mapOf(it.parameters[0] to provider.getter.call(*caller))).toString()
                } else it.call(provider.getter.call(*caller), "", "").toString()
            }
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

fun DescribeSpec.`faker instance is initialized with custom locale`(fakerInit: (AbstractFaker.Builder<AbstractFaker>.() -> Unit) -> AbstractFaker) {
    describe("Faker instance is initialized with custom locale") {
        locales.forEach {
            it("Faker with locale '$it' should be initialized without exceptions") {
                assertDoesNotThrow {
                    fakerInit {
                        fakerConfig {
                            locale = it
                        }
                    }
                }
            }
        }
    }
}

private val locales = listOf(
    "ar",
    "bg",
    "ca",
    "ca-CAT",
    "da-DK",
    "de",
    "de-AT",
    "de-CH",
    "ee",
    "en",
    "en-AU",
    "en-au-ocker",
    "en-BORK",
    "en-CA",
    "en-GB",
    "en-IND",
    "en-KE",
    "en-MS",
    "en-NEP",
    "en-NG",
    "en-NZ",
    "en-PAK",
    "en-SG",
    "en-TH",
    "en-UG",
    "en-US",
    "en-ZA",
    "es",
    "es-AR",
    "es-MX",
    "fa",
    "fi-FI",
    "fr-CA",
    "fr-CH",
    "he",
    "hy",
    "id",
    "it",
    "ko",
    "License",
    "lt",
    "lv",
    "mi-NZ",
    "nb-NO",
    "nl",
    "no",
    "pl",
    "pt",
    "pt-BR",
    "ru",
    "sk",
    "sv",
    "th",
    "tr",
    "uk",
    "vi",
    "zh-CN",
    "zh-TW",
)

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
    "Pom Pom", // superMario#characters
    "Min Min", // superSmashBros#fighter
)

private val valuesWithHashKey = listOf(
    "A# .NET", // programmingLanguage#name
    "A# (Axiom)", // programmingLanguage#name
    "C# – ISO/I EC 23270", //programmingLanguage#name
    "F#", // programmingLanguage#name
    "J#", // programmingLanguage#name
    "M#", // programmingLanguage#name
    "P#", // programmingLanguage#name
    "Q# (Microsoft programming language)", // programmingLanguage#name
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
