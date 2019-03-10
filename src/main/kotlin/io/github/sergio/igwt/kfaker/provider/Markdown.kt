package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.MARKDOWN] category.
 */
class Markdown internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MARKDOWN

    val headers = resolve { fakerService.resolve(Faker, it, "headers") }
    val emphasis = resolve { fakerService.resolve(Faker, it, "emphasis") }
}
