package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ANCIENT] category.
 */
@Suppress("unused")
class Ancient internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Ancient>(fakerService) {
    override val categoryName = CategoryName.ANCIENT
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val god = resolve("god")
    val primordial = resolve("primordial")
    val titan = resolve("titan")
    val hero = resolve("hero")
}
