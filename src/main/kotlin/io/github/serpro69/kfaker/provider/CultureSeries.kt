package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CULTURE_SERIES] category.
 */
@Suppress("unused")
class CultureSeries internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<CultureSeries>(fakerService) {
    override val categoryName = CategoryName.CULTURE_SERIES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val books = resolve("books")
    val cultureShips = resolve("culture_ships")
    val cultureShipClasses = resolve("culture_ship_classes")
    val cultureShipClassAbvs = resolve("culture_ship_class_abvs")
    val civs = resolve("civs")
    val planets = resolve("planets")
}
