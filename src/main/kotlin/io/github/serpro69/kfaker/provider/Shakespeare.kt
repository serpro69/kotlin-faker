package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SHAKESPEARE] category.
 */
@Suppress("unused")
class Shakespeare internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Shakespeare>(fakerService) {
    override val categoryName = CategoryName.SHAKESPEARE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val hamlet = resolve("hamlet")
    val asYouLikeIt = resolve("as_you_like_it")
    val kingRichardTheThird = resolve("king_richard_iii")
    val romeoAndJuliet = resolve("romeo_and_juliet")
}
