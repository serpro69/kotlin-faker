package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ANCIENT] category.
 */
@Suppress("unused")
class Ancient internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Ancient>(fakerService) {
    override val categoryName = CategoryName.ANCIENT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Ancient>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun god() = resolve("god")
    fun primordial() = resolve("primordial")
    fun titan() = resolve("titan")
    fun hero() = resolve("hero")
}
