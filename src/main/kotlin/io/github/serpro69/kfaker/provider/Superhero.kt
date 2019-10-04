package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SUPERHERO] category.
 */
@Suppress("unused")
class Superhero internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Superhero>(fakerService) {
    override val categoryName = CategoryName.SUPERHERO
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    // These properties are needed because the value for `name` property
    // is resolved with these properties through yml expression in the form of `#{Superhero.prefix}`
    internal val prefix = resolve("prefix")
    internal val suffix = resolve("suffix")
    internal val descriptor = resolve("descriptor")

    val power = resolve("power")
    val name = resolve("name")
}
