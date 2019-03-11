package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BOOK] category.
 */
class Book internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BOOK

    val title = resolve { fakerService.resolve(Faker, it, "title") }
    val author = resolve { fakerService.resolve(Faker, it, "author") }
    val publisher = resolve { fakerService.resolve(Faker, it, "publisher") }
    val genre = resolve { fakerService.resolve(Faker, it, "genre") }
}
