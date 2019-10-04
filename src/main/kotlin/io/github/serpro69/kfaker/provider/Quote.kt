package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.QUOTE] category.
 */
@Suppress("unused")
class Quote internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Quote>(fakerService) {
    override val categoryName = CategoryName.QUOTE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val famousLastWords = resolve("famous_last_words")
    val matz = resolve("matz")
    val mostInterestingManInTheWorld = resolve("most_interesting_man_in_the_world")
    val robin = resolve("robin")
    val singularSiegler = resolve("singular_siegler")
    val yoda = resolve("yoda")
}
