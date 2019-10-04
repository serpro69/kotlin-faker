package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HACKER] category.
 */
@Suppress("unused")
class Hacker internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Hacker>(fakerService) {
    override val categoryName = CategoryName.HACKER
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val abbreviation = resolve("abbreviation")
    val adjective = resolve("adjective")
    val noun = resolve("noun")
    val verb = resolve("verb")
    val ingverb = resolve("ingverb")
}
