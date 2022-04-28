package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.LEBOWSKI] category.
 */
@Suppress("unused")
class Lebowski internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Lebowski>(fakerService) {
    override val category = YamlCategory.LEBOWSKI
    override val localUniqueDataProvider = LocalUniqueDataProvider<Lebowski>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
