package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SUPERHERO] category.
 */
@Suppress("unused")
class Superhero internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Superhero>(fakerService) {
    override val categoryName = CategoryName.SUPERHERO
    override val localUniqueDataProvider = LocalUniqueDataProvider<Superhero>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    // These functions are needed because the value for `name` property
    // is resolved with these properties through yml expression in the form of `#{Superhero.prefix}`
    internal fun prefix() = resolve("prefix")
    internal fun suffix() = resolve("suffix")
    internal fun descriptor() = resolve("descriptor")

    fun power() = resolve("power")
    fun name() = resolve("name")
}
