package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MICHAEL_SCOTT] category.
 */
class MichaelScott internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MICHAEL_SCOTT

    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
