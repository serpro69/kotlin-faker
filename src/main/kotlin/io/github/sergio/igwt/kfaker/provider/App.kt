package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class App internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.APP

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val version = resolve { fakerService.resolve(Faker, it, "version") }
    val author = resolve { fakerService.resolve(Faker, it, "author") }
}