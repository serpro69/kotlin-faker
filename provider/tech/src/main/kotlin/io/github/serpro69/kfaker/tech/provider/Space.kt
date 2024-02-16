package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SPACE] category.
 */
@Suppress("unused")
class Space internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Space>(fakerService) {
    override val yamlCategory = YamlCategory.SPACE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Space>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun planet() = resolve("planet")
    fun moon() = resolve("moon")
    fun galaxy() = resolve("galaxy")
    fun nebula() = resolve("nebula")
    fun starCluster() = resolve("star_cluster")
    fun constellation() = resolve("constellation")
    fun star() = resolve("star")
    fun agency() = resolve("agency")
    fun agencyAbv() = resolve("agency_abv")
    fun nasaSpaceCraft() = resolve("nasa_space_craft")
    fun company() = resolve("company")
    fun distanceMeasurement() = resolve("distance_measurement")
    fun meteorite() = resolve("meteorite")
    fun launchVehicle() = resolve("launch_vehicle")
}
