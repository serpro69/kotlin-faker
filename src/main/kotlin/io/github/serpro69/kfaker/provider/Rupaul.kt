package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RUPAUL] category.
 */
class Rupaul internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.RUPAUL

    val queens = resolve { fakerService.resolve(Faker, it, "queens") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
