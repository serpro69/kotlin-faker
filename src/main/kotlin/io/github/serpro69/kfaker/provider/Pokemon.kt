package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Pokemon internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val names = resolve { fakerService.resolve(Faker, it, "pokemon", "names") }
    val locations = resolve { fakerService.resolve(Faker, it, "pokemon", "locations") }
    val moves = resolve { fakerService.resolve(Faker, it, "pokemon", "moves") }
}
