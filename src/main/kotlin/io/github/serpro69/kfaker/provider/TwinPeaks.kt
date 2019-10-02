package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.TWIN_PEAKS] category.
 */
@Suppress("unused")
class TwinPeaks internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.TWIN_PEAKS

    val characters = resolve { fakerService.resolve(it, "characters") }
    val locations = resolve { fakerService.resolve(it, "locations") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
}
