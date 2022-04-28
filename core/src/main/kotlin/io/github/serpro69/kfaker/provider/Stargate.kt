package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.STARGATE] category.
 */
@Suppress("unused")
class Stargate internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Stargate>(fakerService) {
    override val category = YamlCategory.STARGATE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Stargate>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun planets() = resolve("planets")
    fun quotes() = resolve("quotes")
}
