package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.THE_EXPANSE] category.
 */
@Suppress("unused")
class TheExpanse internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<TheExpanse>(fakerService) {
    override val categoryName = CategoryName.THE_EXPANSE
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheExpanse>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun ships() = resolve("ships")
    fun quotes() = resolve("quotes")
}
