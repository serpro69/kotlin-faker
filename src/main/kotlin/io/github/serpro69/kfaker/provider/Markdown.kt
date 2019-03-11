package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MARKDOWN] category.
 */
class Markdown internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MARKDOWN

    val headers = resolve { fakerService.resolve(Faker, it, "headers") }
    val emphasis = resolve { fakerService.resolve(Faker, it, "emphasis") }
}
