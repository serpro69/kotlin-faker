package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.SHAKESPEARE] category.
 */
class Shakespeare internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SHAKESPEARE

    val hamlet = resolve { fakerService.resolve(Faker, it, "hamlet") }
    val asYouLikeIt = resolve { fakerService.resolve(Faker, it, "as_you_like_it") }
    val kingRichardTheThird = resolve { fakerService.resolve(Faker, it, "king_richard_iii") }
    val romeoAndJuliet = resolve { fakerService.resolve(Faker, it, "romeo_and_juliet") }
}
