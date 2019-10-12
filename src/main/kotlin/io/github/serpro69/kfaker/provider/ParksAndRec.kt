package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PARKS_AND_REC] category.
 */
@Suppress("unused")
class ParksAndRec internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ParksAndRec>(fakerService) {
    override val categoryName = CategoryName.PARKS_AND_REC

    fun characters() = resolve("characters")
    fun cities() = resolve("cities")
}
