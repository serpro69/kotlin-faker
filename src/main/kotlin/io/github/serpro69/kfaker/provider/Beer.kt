package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BEER] category.
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
