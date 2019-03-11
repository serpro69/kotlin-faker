package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SPACE] category.
 */
class Space internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SPACE

    val planet = resolve { fakerService.resolve(Faker, it, "planet") }
    val moon = resolve { fakerService.resolve(Faker, it, "moon") }
    val galaxy = resolve { fakerService.resolve(Faker, it, "galaxy") }
    val nebula = resolve { fakerService.resolve(Faker, it, "nebula") }
    val starCluster = resolve { fakerService.resolve(Faker, it, "star_cluster") }
    val constellation = resolve { fakerService.resolve(Faker, it, "constellation") }
    val star = resolve { fakerService.resolve(Faker, it, "star") }
    val agency = resolve { fakerService.resolve(Faker, it, "agency") }
    val agencyAbv = resolve { fakerService.resolve(Faker, it, "agency_abv") }
    val nasaSpaceCraft = resolve { fakerService.resolve(Faker, it, "nasa_space_craft") }
    val company = resolve { fakerService.resolve(Faker, it, "company") }
    val distanceMeasurement = resolve { fakerService.resolve(Faker, it, "distance_measurement") }
    val meteorite = resolve { fakerService.resolve(Faker, it, "meteorite") }
    val launchVehicule = resolve { fakerService.resolve(Faker, it, "launch_vehicule") }
}
