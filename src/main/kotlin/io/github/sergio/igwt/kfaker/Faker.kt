package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.provider.Address
import io.github.sergio.igwt.kfaker.provider.Ancient
import io.github.sergio.igwt.kfaker.provider.Animal
import io.github.sergio.igwt.kfaker.provider.App
import io.github.sergio.igwt.kfaker.provider.Appliance
import io.github.sergio.igwt.kfaker.provider.AquaTeenHungerForce
import io.github.sergio.igwt.kfaker.provider.Artist
import io.github.sergio.igwt.kfaker.provider.BackToTheFuture
import io.github.sergio.igwt.kfaker.provider.Bank
import io.github.sergio.igwt.kfaker.provider.Name
import java.util.Locale

object Faker {
    private lateinit var fakerService: FakerService
    lateinit var address: Address
    lateinit var ancient: Ancient
    lateinit var animal: Animal
    lateinit var name: Name
    lateinit var app: App
    lateinit var appliance: Appliance
    lateinit var aquaTeenHungerForce: AquaTeenHungerForce
    lateinit var artist: Artist
    lateinit var backToTheFuture: BackToTheFuture
    lateinit var bank: Bank

    @JvmOverloads
    fun init(locale: Locale = Locale.ENGLISH): Faker {
        fakerService = FakerService(locale)

        address = Address(fakerService)
        ancient = Ancient(fakerService)
        animal = Animal(fakerService)
        name = Name(fakerService)
        app = App(fakerService)
        appliance = Appliance(fakerService)
        aquaTeenHungerForce = AquaTeenHungerForce(fakerService)
        artist = Artist(fakerService)
        backToTheFuture = BackToTheFuture(fakerService)
        bank = Bank(fakerService)

        return this
    }
}
