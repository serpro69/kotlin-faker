package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.AQUA_TEEN_HUNGER_FORCE] category.
 */
class AquaTeenHungerForce internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.AQUA_TEEN_HUNGER_FORCE

    val character = resolve { fakerService.resolve(Faker, it, "character") }
}