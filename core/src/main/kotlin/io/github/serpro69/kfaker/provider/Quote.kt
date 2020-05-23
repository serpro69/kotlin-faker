package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.QUOTE] category.
 */
@Suppress("unused")
class Quote internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Quote>(fakerService) {
    override val categoryName = CategoryName.QUOTE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Quote>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun famousLastWords() = resolve("famous_last_words")
    fun matz() = resolve("matz")
    fun mostInterestingManInTheWorld() = resolve("most_interesting_man_in_the_world")
    fun robin() = resolve("robin")
    fun singularSiegler() = resolve("singular_siegler")
    fun yoda() = resolve("yoda")
}
