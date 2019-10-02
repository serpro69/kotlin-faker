package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MARKETING] category.
 */
@Suppress("unused")
class Marketing internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MARKETING

    val buzzwords = resolve { fakerService.resolve(it, "buzzwords") }
}
