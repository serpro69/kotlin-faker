package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SPACE] category.
 */
@Suppress("unused")
class Space internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Space>(fakerService) {
    override val categoryName = CategoryName.SPACE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val planet = resolve("planet")
    val moon = resolve("moon")
    val galaxy = resolve("galaxy")
    val nebula = resolve("nebula")
    val starCluster = resolve("star_cluster")
    val constellation = resolve("constellation")
    val star = resolve("star")
    val agency = resolve("agency")
    val agencyAbv = resolve("agency_abv")
    val nasaSpaceCraft = resolve("nasa_space_craft")
    val company = resolve("company")
    val distanceMeasurement = resolve("distance_measurement")
    val meteorite = resolve("meteorite")
    val launchVehicule = resolve("launch_vehicule")
}
