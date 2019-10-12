package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CONSTRUCTION] category.
 */
@Suppress("unused")
class Construction internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Construction>(fakerService) {
    override val categoryName = CategoryName.CONSTRUCTION

    fun materials() = resolve("materials")
}
