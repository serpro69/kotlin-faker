package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BREAKING_BAD] category.
 */
class BreakingBad internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BREAKING_BAD

    val character = resolve { fakerService.resolve(it, "character") }
    val episode = resolve { fakerService.resolve(it, "episode") }
}
