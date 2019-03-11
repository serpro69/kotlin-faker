package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BACK_TO_THE_FUTURE] category.
 */
class BackToTheFuture internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BACK_TO_THE_FUTURE

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val dates = resolve { fakerService.resolve(Faker, it, "dates") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}