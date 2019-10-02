package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FAMILY_GUY] category.
 */
@Suppress("unused")
class FamilyGuy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FAMILY_GUY

    val character = resolve { fakerService.resolve(it, "character") }
    val location = resolve { fakerService.resolve(it, "location") }
    val quote = resolve { fakerService.resolve(it, "quote") }
}
