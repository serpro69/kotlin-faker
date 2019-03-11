package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SILICON_VALLEY] category.
 */
class SiliconValley internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SILICON_VALLEY

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val companies = resolve { fakerService.resolve(Faker, it, "companies") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
    val apps = resolve { fakerService.resolve(Faker, it, "apps") }
    val inventions = resolve { fakerService.resolve(Faker, it, "inventions") }
    val mottos = resolve { fakerService.resolve(Faker, it, "mottos") }
    val urls = resolve { fakerService.resolve(Faker, it, "urls") }
    val email = resolve { fakerService.resolve(Faker, it, "email") }
}
