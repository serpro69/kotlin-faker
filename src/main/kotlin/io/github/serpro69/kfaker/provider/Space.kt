package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SPACE] category.
 */
@Suppress("unused")
class Space internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SPACE

    val planet = resolve { fakerService.resolve(it, "planet") }
    val moon = resolve { fakerService.resolve(it, "moon") }
    val galaxy = resolve { fakerService.resolve(it, "galaxy") }
    val nebula = resolve { fakerService.resolve(it, "nebula") }
    val starCluster = resolve { fakerService.resolve(it, "star_cluster") }
    val constellation = resolve { fakerService.resolve(it, "constellation") }
    val star = resolve { fakerService.resolve(it, "star") }
    val agency = resolve { fakerService.resolve(it, "agency") }
    val agencyAbv = resolve { fakerService.resolve(it, "agency_abv") }
    val nasaSpaceCraft = resolve { fakerService.resolve(it, "nasa_space_craft") }
    val company = resolve { fakerService.resolve(it, "company") }
    val distanceMeasurement = resolve { fakerService.resolve(it, "distance_measurement") }
    val meteorite = resolve { fakerService.resolve(it, "meteorite") }
    val launchVehicule = resolve { fakerService.resolve(it, "launch_vehicule") }
}
