package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VEHICLE] category.
 */
@Suppress("unused")
class Vehicle internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Vehicle>(fakerService) {
    override val categoryName = CategoryName.VEHICLE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Vehicle>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun manufacture() = resolve("manufacture")
    fun makes() = resolve("makes")
    fun modelsByMake(make: String) = resolve("models_by_make", make.toLowerCase())
    fun colors() = resolve("colors")
    fun transmissions() = resolve("transmissions")
    fun driveTypes() = resolve("drive_types")
    fun fuelTypes() = resolve("fuel_types")
    fun styles() = resolve("styles")
    fun carTypes() = resolve("car_types")
    fun carOptions() = resolve("car_options")
    fun standardSpecs() = resolve("standard_specs")
    fun doors() = resolve("doors")
    fun engineSizes() = resolve("engine_sizes")
    fun licensePlate() = resolve("license_plate")
    fun licencePlateByState(stateCode: String) = resolve("license_plate_by_state", stateCode.toLowerCase())
    fun cylinderEngine() = resolve("cylinder_engine")
}
