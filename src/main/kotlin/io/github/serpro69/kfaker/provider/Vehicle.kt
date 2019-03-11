package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.VEHICLE] category.
 */
class Vehicle internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.VEHICLE

    val manufacture = resolve { fakerService.resolve(Faker, it, "manufacture") }
    val makes = resolve { fakerService.resolve(Faker, it, "makes") }
    val modelsByMake: (make: String) -> String = { make ->
        resolve { fakerService.resolve(Faker, it, "models_by_make", make) }.invoke()
    }
    val colors = resolve { fakerService.resolve(Faker, it, "colors") }
    val transmissions = resolve { fakerService.resolve(Faker, it, "transmissions") }
    val driveTypes = resolve { fakerService.resolve(Faker, it, "drive_types") }
    val fuelTypes = resolve { fakerService.resolve(Faker, it, "fuel_types") }
    val styles = resolve { fakerService.resolve(Faker, it, "styles") }
    val carTypes = resolve { fakerService.resolve(Faker, it, "car_types") }
    val carOptions = resolve { fakerService.resolve(Faker, it, "car_options") }
    val standardSpecs = resolve { fakerService.resolve(Faker, it, "standard_specs") }
    val doors = resolve { fakerService.resolve(Faker, it, "doors") }
    val engineSizes = resolve { fakerService.resolve(Faker, it, "engine_sizes") }
    val licensePlate = resolve { fakerService.resolve(Faker, it, "license_plate") }
    val licencePlateByState: (stateCode: String) -> String = { stateCode ->
        resolve { fakerService.resolve(Faker, it, "licenc_plate_by_state", stateCode) }.invoke()
    }
    val cylinderEngine = resolve { fakerService.resolve(Faker, it, "cylinder_engine") }
}
