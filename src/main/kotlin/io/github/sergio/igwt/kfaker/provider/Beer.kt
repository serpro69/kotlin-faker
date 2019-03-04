package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class Beer internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BEER

    val brand = resolve { fakerService.resolve(Faker, it, "brand") }
    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val hop = resolve { fakerService.resolve(Faker, it, "hop") }
    val yeast = resolve { fakerService.resolve(Faker, it, "yeast") }
    val malt = resolve { fakerService.resolve(Faker, it, "malt") }
    val style = resolve { fakerService.resolve(Faker, it, "style") }
}
