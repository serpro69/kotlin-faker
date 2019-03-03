package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.provider.Address
import io.github.sergio.igwt.kfaker.provider.Ancient
import io.github.sergio.igwt.kfaker.provider.Animal
import io.github.sergio.igwt.kfaker.provider.Name
import java.util.Locale

object Faker {
    private lateinit var fakerService: FakerService
    lateinit var address: Address
    lateinit var ancient: Ancient
    lateinit var animal: Animal
    lateinit var name: Name

    @JvmOverloads
    fun init(locale: Locale = Locale.ENGLISH): Faker {
        fakerService = FakerService(locale)

        address = Address(fakerService)
        ancient = Ancient(fakerService)
        animal = Animal(fakerService)
        name = Name(fakerService)

        return this
    }
}
