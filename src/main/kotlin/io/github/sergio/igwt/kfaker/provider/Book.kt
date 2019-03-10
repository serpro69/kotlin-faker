package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
