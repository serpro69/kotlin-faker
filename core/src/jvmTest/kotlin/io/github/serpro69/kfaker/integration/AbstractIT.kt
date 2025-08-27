package io.github.serpro69.kfaker.integration

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.Money
import io.github.serpro69.kfaker.provider.misc.RandomProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerScope
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

abstract class AbstractIT : DescribeSpec {

    private constructor() : super()

    constructor(
        body: DescribeSpec.(AbstractIT) -> Unit
    ) : super({ body(this, object : AbstractIT() {}) })

    init {
        assertSoftly = true
    }

    val duplicatedValues =
        listOf(
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

    val valuesWithHashKey =
        listOf(
            "A# .NET", // programmingLanguage#name
            "A# (Axiom)", // programmingLanguage#name
            "C# – ISO/I EC 23270", // programmingLanguage#name
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
}

@Suppress("FunctionName")
fun DescribeSpec.`describe all public generators`(
    test: suspend DescribeSpecContainerScope.(Faker, KProperty<*>, KFunction<*>) -> Unit
) {
    val faker = Faker()

    // Get a list of all publicly visible providers
    val providers: List<KProperty<*>> =
        faker::class.declaredMemberProperties.filter {
            it.visibility == KVisibility.PUBLIC &&
                it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType) &&
                it.returnType.classifier !=
                    Money::class // Ignore Money provider as it's a special case
                &&
                it.returnType.classifier != StringProvider::class // Ignore String provider
                &&
                it.returnType.classifier != RandomProvider::class // Ignore String provider
        }

    // Get a list of all publicly visible functions in each provider
    val providerFunctions: GeneratorFunctions =
        providers.associateBy { provider ->
            provider.getter.call(faker)!!::class.declaredMemberFunctions.filter {
                it.visibility == KVisibility.PUBLIC &&
                    !it.annotations.any { ann -> ann is Deprecated }
            }
        }
    describe("all public functions in each provider") {
        providerFunctions.forEach { (functions, provider) ->
            functions.forEach {
                context("result value for ${provider.name}#${it.name} is resolved correctly") {
                    test(this, faker, provider, it)
                }
            }
        }
    }
}

@Suppress("FunctionName")
fun DescribeSpec.`describe all public sub-providers`(
    test: suspend DescribeSpecContainerScope.(FakeDataProvider, KProperty<*>, KFunction<*>) -> Unit
) {
    val faker = Faker()

    // Get a list of all publicly visible providers
    val providers: List<KProperty<*>> =
        faker::class.declaredMemberProperties.filter {
            it.visibility == KVisibility.PUBLIC &&
                it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType) &&
                // Ignore Money provider as it's a special case
                it.returnType.classifier != Money::class &&
                // Ignore String provider
                it.returnType.classifier != StringProvider::class &&
                // Ignore Random provider
                it.returnType.classifier != RandomProvider::class
        }

    // Get a list of all publicly visible sub-providers in each provider
    val subProviders: List<Pair<FakeDataProvider, List<KProperty<*>>>> =
        providers.mapNotNull { p ->
            val provider = p.getter.call(faker)!! as FakeDataProvider
            val subs =
                provider::class.declaredMemberProperties.filter {
                    it.visibility == KVisibility.PUBLIC &&
                        it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType) &&
                        // exclude 'unique' properties
                        it.returnType.classifier as KClass<*> != provider::class
                }
            if (subs.isNotEmpty()) provider to subs else null
        }

    // Get a list of all publicly visible functions in each provider
    val providerFunctions: GeneratorFunctions =
        providers.associateBy { provider ->
            provider.getter.call(faker)!!::class.declaredMemberFunctions.filter {
                it.visibility == KVisibility.PUBLIC &&
                    !it.annotations.any { ann -> ann is Deprecated }
            }
        }

    // Get all publicly visible functions in each sub-provider
    val subProviderFunctions =
        subProviders.map { (provider, subs) ->
            provider to
                subs.associateBy { sub ->
                    sub.getter.call(provider)!!::class.declaredMemberFunctions.filter {
                        it.visibility == KVisibility.PUBLIC &&
                            !it.annotations.any { ann -> ann is Deprecated }
                    }
                }
        }

    describe("all public functions in each sub-provider") {
        subProviderFunctions.forEach { (provider, subs) ->
            subs.forEach { (functions, sub) ->
                functions.forEach {
                    context("result value for ${sub.name}#${it.name} is resolved correctly") {
                        test(this, provider, sub, it)
                    }
                }
            }
        }
    }
}

typealias GeneratorFunctions = Map<List<KFunction<*>>, KProperty<*>>
