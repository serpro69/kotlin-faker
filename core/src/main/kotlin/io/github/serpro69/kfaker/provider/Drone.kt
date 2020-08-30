package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DRONE] category.
 */
@Suppress("unused")
class Drone internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Drone>(fakerService) {
    override val categoryName = CategoryName.DRONE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Drone>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
    fun weight() = resolve("weight")
    fun maxAscentSpeed() = resolve("max_ascent_speed")
    fun maxDescentSpeed() = resolve("max_descent_speed")
    fun flightTime() = resolve("flight_time")
    fun maxAltitude() = resolve("max_altitude")
    fun maxFlightDistance() = resolve("max_flight_distance")
    fun maxSpeed() = resolve("max_speed")
    fun maxWindResistance() = resolve("max_wind_resistance")
    fun maxAngularVelocity() = resolve("max_angular_velocity")
    fun maxTiltAngle() = resolve("max_tilt_angle")
    fun operatingTemperature() = resolve("operating_temperature")
    fun batteryCapacity() = resolve("battery_capacity")
    fun batteryVoltage() = resolve("battery_voltage")
    fun batteryType() = resolve("battery_type")
    fun batteryWeight() = resolve("battery_weight")
    fun chargingTemperature() = resolve("charging_temperature")
    fun maxChargingPower() = resolve("max_charging_power")
    fun iso() = resolve("iso")
    fun maxResolution() = resolve("max_resolution")
    fun photoFormat() = resolve("photo_format")
    fun videoFormat() = resolve("video_format")
    fun maxShutterSpeed() = resolve("max_shutter_speed")
    fun minShutterSpeed() = resolve("min_shutter_speed")
    fun shutterSpeedUnits() = resolve("shutter_speed_units")
}