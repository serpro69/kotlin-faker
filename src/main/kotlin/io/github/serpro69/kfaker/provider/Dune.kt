package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DUNE] category.
 */
class Dune internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DUNE

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val titles = resolve { fakerService.resolve(Faker, it, "titles") }
    val planets = resolve { fakerService.resolve(Faker, it, "planets") }
    val quotes: (character: String) -> String = { character ->
        resolve { fakerService.resolve(Faker, it, "quotes", character.toLowerCase().replace("_", " ")) }.invoke()
    }
    val sayings: (origin: String) -> String = { origin ->
        resolve { fakerService.resolve(Faker, it, "sayings", origin.toLowerCase().replace("_", " ")) }.invoke()
    }

    // TODO: 3/10/2019 would it be better to have enums for functions such as `quotes` to offer constrained number of values for `character`
}
