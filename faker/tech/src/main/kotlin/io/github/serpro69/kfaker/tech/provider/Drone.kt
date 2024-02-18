package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DRONE] category.
 */
@Suppress("unused")
class Drone internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Drone>(fakerService) {
    override val yamlCategory = YamlCategory.DRONE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Drone>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun name() = resolve("name")
    fun weight() = with(fakerService) { resolve("weight").numerify() }
    fun maxAscentSpeed() = with(fakerService) { resolve("max_ascent_speed").numerify() }
    fun maxDescentSpeed() = with(fakerService) { resolve("max_descent_speed").numerify() }
    fun flightTime() = with(fakerService) { resolve("flight_time").numerify() }
    fun maxAltitude() = with(fakerService) { resolve("max_altitude").numerify() }
    fun maxFlightDistance() = with(fakerService) { resolve("max_flight_distance").numerify() }
    fun maxSpeed() = with(fakerService) { resolve("max_speed").numerify() }
    fun maxWindResistance() = with(fakerService) { resolve("max_wind_resistance").numerify() }
    fun maxAngularVelocity() = with(fakerService) { resolve("max_angular_velocity").numerify() }
    fun maxTiltAngle() = with(fakerService) { resolve("max_tilt_angle").numerify() }
    fun operatingTemperature() = with(fakerService) { resolve("operating_temperature").numerify() }
    fun batteryCapacity() = with(fakerService) { resolve("battery_capacity").numerify() }
    fun batteryVoltage() = with(fakerService) { resolve("battery_voltage").numerify() }
    fun batteryType() = resolve("battery_type")
    fun batteryWeight() = with(fakerService) { resolve("battery_weight").numerify() }
    fun chargingTemperature() = with(fakerService) { resolve("charging_temperature").numerify() }
    fun maxChargingPower() = with(fakerService) { resolve("max_charging_power").numerify() }
    fun iso() = resolve("iso")
    fun maxResolution() = with(fakerService) { resolve("max_resolution").numerify() }
    fun photoFormat() = resolve("photo_format")
    fun videoFormat() = resolve("video_format")
    fun maxShutterSpeed() = resolve("max_shutter_speed")
    fun minShutterSpeed() = resolve("min_shutter_speed")
    fun shutterSpeedUnits() = resolve("shutter_speed_units")
}
