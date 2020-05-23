package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HITCHHIKERS_GUIDE_TO_THE_GALAXY] category.
 */
@Suppress("unused")
class HitchhikersGuideToTheGalaxy internal constructor(
    fakerService: FakerService
) : AbstractFakeDataProvider<HitchhikersGuideToTheGalaxy>(fakerService) {
    override val categoryName = CategoryName.HITCHHIKERS_GUIDE_TO_THE_GALAXY
    override val localUniqueDataProvider = LocalUniqueDataProvider<HitchhikersGuideToTheGalaxy>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun marvinQuote() = resolve("marvin_quote")
    fun planets() = resolve("planets")
    fun quotes() = resolve("quotes")
    fun species() = resolve("species")
    fun starships() = resolve("starships")
}
