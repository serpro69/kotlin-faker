package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RESTAURANT] category.
 */
class Restaurant internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.RESTAURANT

    val name = resolve { fakerService.resolve(it, "name") }
    val type = resolve { fakerService.resolve(it, "type") }
    val description = resolve { fakerService.resolve(it, "description") }
    val review = resolve { fakerService.resolve(it, "review") }
}
