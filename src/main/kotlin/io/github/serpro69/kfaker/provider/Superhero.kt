package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SUPERHERO] category.
 */
class Superhero internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SUPERHERO

    val power = resolve { fakerService.resolve(Faker, it, "power") }
    val prefix = resolve { fakerService.resolve(Faker, it, "prefix") }
    val suffix = resolve { fakerService.resolve(Faker, it, "suffix") }
    val descriptor = resolve { fakerService.resolve(Faker, it, "descriptor") }
    val name = resolve { fakerService.resolve(Faker, it, "name") }
}
