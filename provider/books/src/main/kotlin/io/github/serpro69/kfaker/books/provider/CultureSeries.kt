package io.github.serpro69.kfaker.books.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CULTURE_SERIES] category.
 */
@Suppress("unused")
class CultureSeries internal constructor(fakerService: FakerService) : YamlFakeDataProvider<CultureSeries>(fakerService) {
    override val yamlCategory = YamlCategory.CULTURE_SERIES
    override val localUniqueDataProvider = LocalUniqueDataProvider<CultureSeries>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun books() = resolve("books")
    fun cultureShips() = resolve("culture_ships")
    fun cultureShipClasses() = resolve("culture_ship_classes")
    fun cultureShipClassAbvs() = resolve("culture_ship_class_abvs")
    fun civs() = resolve("civs")
    fun planets() = resolve("planets")
}
