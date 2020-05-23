package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BEER] category.
 */
@Suppress("unused")
class Beer internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Beer>(fakerService) {
    override val categoryName = CategoryName.BEER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Beer>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun brand() = resolve("brand")
    fun name() = resolve("name")
    fun hop() = resolve("hop")
    fun yeast() = resolve("yeast")
    fun malt() = resolve("malt")
    fun style() = resolve("style")
}
