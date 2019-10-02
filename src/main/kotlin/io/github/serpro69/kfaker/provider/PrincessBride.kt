package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PRINCESS_BRIDE] category.
 */
@Suppress("unused")
class PrincessBride internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PRINCESS_BRIDE

    val characters = resolve { fakerService.resolve(it, "characters") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
}
