package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.RESTAURANT] category.
 */
class Restaurant internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.RESTAURANT

    val name = resolve { TODO("Add 'letterify()` for `?`"); fakerService.resolve(Faker, it, "name") }
    val type = resolve { fakerService.resolve(Faker, it, "type") }
    val description = resolve { fakerService.resolve(Faker, it, "description") }
    val review = resolve { fakerService.resolve(Faker, it, "review") }
}
