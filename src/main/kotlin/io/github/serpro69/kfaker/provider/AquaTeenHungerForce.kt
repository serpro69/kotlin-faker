package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.AQUA_TEEN_HUNGER_FORCE] category.
 */
class AquaTeenHungerForce internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.AQUA_TEEN_HUNGER_FORCE

    val character = resolve { fakerService.resolve(Faker, it, "character") }
}