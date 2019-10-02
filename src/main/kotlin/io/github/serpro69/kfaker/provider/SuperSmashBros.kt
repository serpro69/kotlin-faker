package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class SuperSmashBros internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val fighter = resolve { fakerService.resolve(it, "super_smash_bros", "fighter") }
    val stage = resolve { fakerService.resolve(it, "super_smash_bros", "stage") }
}
