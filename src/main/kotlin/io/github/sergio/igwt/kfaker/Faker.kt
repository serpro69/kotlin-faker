package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.provider.Name

object Faker {
    private val fakerService = FakerService()

    val name = Name(fakerService)
}
