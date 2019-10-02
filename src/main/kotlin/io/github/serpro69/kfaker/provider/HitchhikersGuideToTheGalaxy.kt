package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HITCHHIKERS_GUIDE_TO_THE_GALAXY] category.
 */
class HitchhikersGuideToTheGalaxy internal constructor(
    fakerService: FakerService
) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HITCHHIKERS_GUIDE_TO_THE_GALAXY

    val characters = resolve { fakerService.resolve(it, "characters") }
    val locations = resolve { fakerService.resolve(it, "locations") }
    val marvinQuote = resolve { fakerService.resolve(it, "marvin_quote") }
    val planets = resolve { fakerService.resolve(it, "planets") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
    val species = resolve { fakerService.resolve(it, "species") }
    val starships = resolve { fakerService.resolve(it, "starships") }
}
