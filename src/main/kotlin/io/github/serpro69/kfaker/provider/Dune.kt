package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DUNE] category.
 */
@Suppress("unused")
class Dune internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Dune>(fakerService) {
    override val categoryName = CategoryName.DUNE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val titles = resolve("titles")
    val planets = resolve("planets")
    val quotes: (character: String) -> String = { character ->
        resolve { fakerService.resolve(it, "quotes", character.toLowerCase().replace("_", " ")) }.invoke()
    }
    val sayings: (origin: String) -> String = { origin ->
        resolve { fakerService.resolve(it, "sayings", origin.toLowerCase().replace("_", " ")) }.invoke()
    }

    // TODO: 3/10/2019 would it be better to have enums for functions such as `quotes` to offer constrained number of values for `character`
}
