package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.LORD_OF_THE_RINGS] category.
 */
@Suppress("unused")
class LordOfTheRings internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<LordOfTheRings>(fakerService) {
    override val categoryName = CategoryName.LORD_OF_THE_RINGS
    override val localUniqueDataProvider = LocalUniqueDataProvider<LordOfTheRings>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
}
