package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SPACE] category.
 */
@Suppress("unused")
class Space internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Space>(fakerService) {
    override val categoryName = CategoryName.SPACE

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
    fun launchVehicule() = resolve("launch_vehicule")
}
