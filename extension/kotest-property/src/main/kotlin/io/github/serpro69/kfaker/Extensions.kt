package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.misc.RandomProviderConfig
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

val Arb.Companion.randomClass: RandomClassFaker
    get() = RandomClassFaker(Faker())

class RandomClassFaker internal constructor(val faker: Faker) {
    inline fun <reified T : Any> instance(crossinline configurator: RandomProviderConfig.() -> Unit = {}): Arb<T> {
        return arbitrary { faker.randomClass.randomClassInstance(configurator) }
    }
}
