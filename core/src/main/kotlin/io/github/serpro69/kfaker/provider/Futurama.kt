package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FUTURAMA] category.
 */
@Suppress("unused")
class Futurama internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Futurama>(fakerService) {
    override val categoryName = CategoryName.FUTURAMA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Futurama>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
    fun hermesCatchphrases() = resolve("hermes_catchphrases")
}