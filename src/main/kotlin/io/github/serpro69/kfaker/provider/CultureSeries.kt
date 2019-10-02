package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CULTURE_SERIES] category.
 */
class CultureSeries internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CULTURE_SERIES

    val books = resolve { fakerService.resolve(it, "books") }
    val cultureShips = resolve { fakerService.resolve(it, "culture_ships") }
    val cultureShipClasses = resolve { fakerService.resolve(it, "culture_ship_classes") }
    val cultureShipClassAbvs = resolve { fakerService.resolve(it, "culture_ship_class_abvs") }
    val civs = resolve { fakerService.resolve(it, "civs") }
    val planets = resolve { fakerService.resolve(it, "planets") }
}
