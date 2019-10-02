package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.APP] category.
 */
class App internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.APP

    val name = resolve { fakerService.resolve(it, "name") }
    val version = resolve { fakerService.resolve(it, "version") }
    val author = resolve { fakerService.resolve(it, "author") }
}