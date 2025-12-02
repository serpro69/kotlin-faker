package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.misc.RandomProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider
import io.github.serpro69.kfaker.provider.Person
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType

/**
 * Introspects [faker] and it's descendants.
 */
class Introspector(private val faker: AbstractFaker) {
    // Get a list of all publicly visible providers
    val providers: Sequence<KProperty<*>> = faker::class.declaredMemberProperties.asSequence().filter {
        it.visibility == KVisibility.PUBLIC
            && (it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType) || it.returnType.classifier == Person::class)
            && it.returnType.classifier != StringProvider::class // Ignore this one as it's "special"
            && it.returnType.classifier != RandomProvider::class // Ignore this one as it's "special"
    }

    // Get a list of all publicly visible functions and sub-provider properties in each provider
    val providerData: Map<KProperty<*>, Pair<Sequence<KFunction<*>>, Map<KProperty<*>, Sequence<KFunction<*>>>>> = providers.associateBy { provider ->
        val providerInstance = provider.getter.call(faker)!!

        val functions = providerInstance::class.declaredMemberFunctions.asSequence().filter {
            it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
        }
        val properties = providerInstance::class.declaredMemberProperties.asSequence().filter {
            it.visibility == KVisibility.PUBLIC
                && it.returnType.isSubtypeOf(FakeDataProvider::class.starProjectedType)
                && !it.annotations.any { ann -> ann is Deprecated }
                && it.name != "unique"
        }.associateBy { sub: KProperty<*> ->
            sub.getter.call(providerInstance)!!::class.declaredMemberFunctions.asSequence().filter {
                it.visibility == KVisibility.PUBLIC && !it.annotations.any { ann -> ann is Deprecated }
            }
        }.map { it.value as KProperty<*> to it.key }.toMap()
        (functions to properties)
    }.map { it.value to it.key }.toMap()
}
