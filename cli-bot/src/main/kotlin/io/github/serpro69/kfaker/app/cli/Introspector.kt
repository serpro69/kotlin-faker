package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.AbstractFakeDataProvider
import java.lang.reflect.Modifier

/**
 * Introspects [Faker] and it's descendants.
 */
class Introspector(private val faker: Faker) {
    // Get a list of all publicly visible providers
    val providers = faker.javaClass.declaredMethods.filter {
        Modifier.isPublic(it.modifiers) && it.returnType.superclass == AbstractFakeDataProvider::class.java
    }

    // Get a list of all publicly visible functions in each provider
    val providerFunctions = providers.associateBy { provider ->
        provider.invoke(faker)!!.javaClass.declaredMethods.filter {
            Modifier.isPublic(it.modifiers)
                    && !it.annotations.any { ann -> ann is Deprecated }
                    && it.returnType == String::class.java
        }
    }.map { it.value to it.key }.toMap()
}
