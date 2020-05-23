package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ANCIENT] category.
 */
@Suppress("unused")
class Chiquito internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Chiquito>(fakerService) {
    override val categoryName = CategoryName.CHIQUITO
    override val localUniqueDataProvider = LocalUniqueDataProvider<Chiquito>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun expressions() = resolve("expressions")
    fun terms() = resolve("terms")
    fun sentences() = resolve("sentences")
    fun jokes() = resolve("jokes")
}
