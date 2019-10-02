package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VEHICLE] category.
 */
@Suppress("unused")
class Vehicle internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.VEHICLE

    val manufacture = resolve { fakerService.resolve(it, "manufacture") }
    val makes = resolve { fakerService.resolve(it, "makes") }
    val modelsByMake: (make: String) -> String = { make ->
        resolve { fakerService.resolve(it, "models_by_make", make.toLowerCase()) }.invoke()
    }
    val colors = resolve { fakerService.resolve(it, "colors") }
    val transmissions = resolve { fakerService.resolve(it, "transmissions") }
    val driveTypes = resolve { fakerService.resolve(it, "drive_types") }
    val fuelTypes = resolve { fakerService.resolve(it, "fuel_types") }
    val styles = resolve { fakerService.resolve(it, "styles") }
    val carTypes = resolve { fakerService.resolve(it, "car_types") }
    val carOptions = resolve { fakerService.resolve(it, "car_options") }
    val standardSpecs = resolve { fakerService.resolve(it, "standard_specs") }
    val doors = resolve { fakerService.resolve(it, "doors") }
    val engineSizes = resolve { fakerService.resolve(it, "engine_sizes") }
    val licensePlate = resolve { fakerService.resolve(it, "license_plate") }
    val licencePlateByState: (stateCode: String) -> String = { stateCode ->
        resolve { fakerService.resolve(it, "license_plate_by_state", stateCode.toLowerCase()) }.invoke()
    }
    val cylinderEngine = resolve { fakerService.resolve(it, "cylinder_engine") }
}
