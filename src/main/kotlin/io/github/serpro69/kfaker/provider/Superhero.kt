package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
