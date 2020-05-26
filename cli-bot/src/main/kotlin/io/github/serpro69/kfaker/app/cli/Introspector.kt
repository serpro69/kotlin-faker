package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.*
import kotlin.reflect.*
import kotlin.reflect.full.*

/**
 * Introspects [Faker] and it's descendants.
 */
class Introspector(private val faker: Faker) {
    // Get a list of all publicly visible providers
    val providers: List<KProperty<*>> = faker::class.declaredMemberProperties.filter {
        it.visibility == KVisibility.PUBLIC && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
    }

    // Get a list of all publicly visible functions in each provider
    val providerFunctions = providers.associateBy { provider ->
        provider.getter.call(faker)!!::class.declaredMemberFunctions.filter {
            it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
        }
    }.map { it.value to it.key }.toMap()
}