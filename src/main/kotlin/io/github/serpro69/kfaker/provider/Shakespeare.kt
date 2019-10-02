package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SHAKESPEARE] category.
 */
class Shakespeare internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SHAKESPEARE

    val hamlet = resolve { fakerService.resolve(it, "hamlet") }
    val asYouLikeIt = resolve { fakerService.resolve(it, "as_you_like_it") }
    val kingRichardTheThird = resolve { fakerService.resolve(it, "king_richard_iii") }
    val romeoAndJuliet = resolve { fakerService.resolve(it, "romeo_and_juliet") }
}
