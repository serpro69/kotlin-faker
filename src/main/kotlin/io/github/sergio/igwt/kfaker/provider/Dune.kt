package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.DUNE] category.
 */
class Dune internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DUNE

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val titles = resolve { fakerService.resolve(Faker, it, "titles") }
    val planets = resolve { fakerService.resolve(Faker, it, "planets") }
    val quotes: (character: String) -> String = { character ->
        resolve { fakerService.resolve(Faker, it, "quotes", character) }.invoke()
    }
    val sayings: (origin: String) -> String = { origin ->
        resolve { fakerService.resolve(Faker, it, "sayings", origin) }.invoke()
    }

    // TODO: 3/10/2019 would it be better to have enums for functions such as `quotes` to offer constrained number of values for `character`
}
