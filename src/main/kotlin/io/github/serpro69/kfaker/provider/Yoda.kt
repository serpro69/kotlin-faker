package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.YODA] category.
 */
@Suppress("unused")
class Yoda internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.YODA

    val quotes = resolve { fakerService.resolve(it, "quotes") }
}
