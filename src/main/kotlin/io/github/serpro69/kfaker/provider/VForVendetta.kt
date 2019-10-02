package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.V_FOR_VENDETTA] category.
 */
class VForVendetta internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.V_FOR_VENDETTA

    val characters = resolve { fakerService.resolve(it, "characters") }
    val speeches = resolve { fakerService.resolve(it, "speeches") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
}
