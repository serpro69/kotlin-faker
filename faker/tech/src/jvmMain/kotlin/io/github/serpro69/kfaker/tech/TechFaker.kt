package io.github.serpro69.kfaker.tech

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.tech.provider.App
import io.github.serpro69.kfaker.tech.provider.Appliance
import io.github.serpro69.kfaker.tech.provider.Camera
import io.github.serpro69.kfaker.tech.provider.Computer
import io.github.serpro69.kfaker.tech.provider.CryptoCoin
import io.github.serpro69.kfaker.tech.provider.Device
import io.github.serpro69.kfaker.tech.provider.Drone
import io.github.serpro69.kfaker.tech.provider.ElectricalComponents
import io.github.serpro69.kfaker.tech.provider.Hacker
import io.github.serpro69.kfaker.tech.provider.ProgrammingLanguage
import io.github.serpro69.kfaker.tech.provider.Space
import io.github.serpro69.kfaker.tech.provider.Vehicle

/** Typealias for the [TechFaker] */
typealias Faker = TechFaker

/**
 * Provides access to fake data generators within the Tech domain.
 *
 * Each category (generator) from this [TechFaker] is represented by a property that (usually) has
 * the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class TechFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    val app: App by lazy { App(fakerService) }
    val appliance: Appliance by lazy { Appliance(fakerService) }
    val camera: Camera by lazy { Camera(fakerService) }
    val computer: Computer by lazy { Computer(fakerService) }
    val cryptoCoin: CryptoCoin by lazy { CryptoCoin(fakerService) }
    val device: Device by lazy { Device(fakerService) }
    val drone: Drone by lazy { Drone(fakerService) }
    val electricalComponents: ElectricalComponents by lazy { ElectricalComponents(fakerService) }
    val hacker: Hacker by lazy { Hacker(fakerService) }
    val programmingLanguage: ProgrammingLanguage by lazy { ProgrammingLanguage(fakerService) }
    val space: Space by lazy { Space(fakerService) }
    val vehicle: Vehicle by lazy { Vehicle(fakerService) }

    @FakerDsl
    /** DSL builder for creating instances of [Faker] */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /** Builds an instance of [Faker] with this [config]. */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [TechFaker.Builder] and returns as an instance of [TechFaker]
 * from that builder.
 */
fun faker(block: TechFaker.Builder.() -> Unit): TechFaker = TechFaker.Builder().apply(block).build()
