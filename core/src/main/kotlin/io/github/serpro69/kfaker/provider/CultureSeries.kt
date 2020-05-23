package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CULTURE_SERIES] category.
 */
@Suppress("unused")
class CultureSeries internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<CultureSeries>(fakerService) {
    override val categoryName = CategoryName.CULTURE_SERIES
    override val localUniqueDataProvider = LocalUniqueDataProvider<CultureSeries>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun books() = resolve("books")
    fun cultureShips() = resolve("culture_ships")
    fun cultureShipClasses() = resolve("culture_ship_classes")
    fun cultureShipClassAbvs() = resolve("culture_ship_class_abvs")
    fun civs() = resolve("civs")
    fun planets() = resolve("planets")
}
