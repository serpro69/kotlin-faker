package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VENTURE_BROS] category.
 */
class VentureBros internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.VENTURE_BROS

    val character = resolve { fakerService.resolve(it, "character") }
    val organization = resolve { fakerService.resolve(it, "organization") }
    val vehicle = resolve { fakerService.resolve(it, "vehicle") }
    val quote = resolve { fakerService.resolve(it, "quote") }
}
