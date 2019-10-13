package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CHUCK_NORRIS] category.
 */
@Suppress("unused")
class ChuckNorris internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ChuckNorris>(fakerService) {
    override val categoryName = CategoryName.CHUCK_NORRIS
    override val uniqueDataProvider = UniqueDataProvider<ChuckNorris>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun fact() = resolve("fact")
}
