package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HITCHHIKERS_GUIDE_TO_THE_GALAXY] category.
 */
@Suppress("unused")
class HitchhikersGuideToTheGalaxy internal constructor(
    fakerService: FakerService
) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HITCHHIKERS_GUIDE_TO_THE_GALAXY

    val characters = resolve("characters")
    val locations = resolve("locations")
    val marvinQuote = resolve("marvin_quote")
    val planets = resolve("planets")
    val quotes = resolve("quotes")
    val species = resolve("species")
    val starships = resolve("starships")
}
