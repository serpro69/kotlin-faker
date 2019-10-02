package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_TREK] category.
 */
@Suppress("unused")
class StarTrek internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STAR_TREK

    val character = resolve { fakerService.resolve(it, "character") }
    val location = resolve { fakerService.resolve(it, "location") }
    val specie = resolve { fakerService.resolve(it, "specie") }
    val villain = resolve { fakerService.resolve(it, "villain") }
}
