package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VEHICLE] category.
 */
@Suppress("unused")
class Vehicle internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Vehicle>(fakerService) {
    override val categoryName = CategoryName.VEHICLE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val manufacture = resolve("manufacture")
    val makes = resolve("makes")
    val modelsByMake: (make: String) -> String = { make ->
        resolve { fakerService.resolve(it, "models_by_make", make.toLowerCase()) }.invoke()
    }
    val colors = resolve("colors")
    val transmissions = resolve("transmissions")
    val driveTypes = resolve("drive_types")
    val fuelTypes = resolve("fuel_types")
    val styles = resolve("styles")
    val carTypes = resolve("car_types")
    val carOptions = resolve("car_options")
    val standardSpecs = resolve("standard_specs")
    val doors = resolve("doors")
    val engineSizes = resolve("engine_sizes")
    val licensePlate = resolve("license_plate")
    val licencePlateByState: (stateCode: String) -> String = { stateCode ->
        resolve { fakerService.resolve(it, "license_plate_by_state", stateCode.toLowerCase()) }.invoke()
    }
    val cylinderEngine = resolve("cylinder_engine")
}
