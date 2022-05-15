package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DUNE] category.
 */
@Suppress("unused")
class Dune internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Dune>(fakerService) {
    override val category = YamlCategory.DUNE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dune>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun titles() = resolve("titles")
    fun planets() = resolve("planets")
    fun cities() = resolve("cities")
    fun quotes(character: String) = resolve("quotes", character.lowercase().replace("_", " "))
    fun sayings(origin: String) = resolve("sayings", origin.lowercase().replace("_", " "))

    // TODO: 3/10/2019 would it be better to have enums for functions such as `quotes` to offer constrained number of values for `character`
}
