package com.github.sergio.igwt.kfaker

import com.github.sergio.igwt.kfaker.provider.Name

object Faker {
    private val fakerService = FakerService()

    val name = Name(fakerService)
}
