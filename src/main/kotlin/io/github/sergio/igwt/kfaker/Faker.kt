package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.provider.Address
import io.github.sergio.igwt.kfaker.provider.Ancient
import io.github.sergio.igwt.kfaker.provider.Animal
import io.github.sergio.igwt.kfaker.provider.Name

object Faker {
    private val fakerService = FakerService()

    val address = Address(fakerService)
    val ancient = Ancient(fakerService)
    val animal = Animal(fakerService)
    val name = Name(fakerService)
}
