package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HACKER] category.
 */
@Suppress("unused")
class Hacker internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Hacker>(fakerService) {
    override val categoryName = CategoryName.HACKER
    override val uniqueDataProvider = UniqueDataProvider<Hacker>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun abbreviation() = resolve("abbreviation")
    fun adjective() = resolve("adjective")
    fun noun() = resolve("noun")
    fun verb() = resolve("verb")
    fun ingverb() = resolve("ingverb")
}
