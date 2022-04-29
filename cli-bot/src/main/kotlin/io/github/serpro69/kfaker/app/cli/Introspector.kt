package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

/**
 * Introspects [Faker] and it's descendants.
 */
class Introspector(private val faker: Faker) {
    // Get a list of all publicly visible providers
    val providers: Sequence<KProperty<*>> = faker::class.declaredMemberProperties.asSequence().filter {
        it.visibility == KVisibility.PUBLIC
            && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
            && it.returnType.classifier != StringProvider::class // Ignore this one as it's "special"
    }

    // Get a list of all publicly visible functions in each provider
    val providerFunctions: Map<KProperty<*>, Sequence<KFunction<*>>> = providers.associateBy { provider ->
        provider.getter.call(faker)!!::class.declaredMemberFunctions.asSequence().filter {
            it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
        }
    }.map { it.value to it.key }.toMap()
}
