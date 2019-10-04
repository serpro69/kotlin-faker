package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BEER] category.
 */
@Suppress("unused")
class Beer internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Beer>(fakerService) {
    override val categoryName = CategoryName.BEER
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val brand = resolve("brand")
    val name = resolve("name")
    val hop = resolve("hop")
    val yeast = resolve("yeast")
    val malt = resolve("malt")
    val style = resolve("style")
}
