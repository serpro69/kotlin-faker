package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
