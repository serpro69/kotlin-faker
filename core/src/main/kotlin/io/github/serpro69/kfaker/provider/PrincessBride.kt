package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PRINCESS_BRIDE] category.
 */
@Suppress("unused")
class PrincessBride internal constructor(fakerService: FakerService) : YamlFakeDataProvider<PrincessBride>(fakerService) {
    override val yamlCategory = YamlCategory.PRINCESS_BRIDE
    override val localUniqueDataProvider = LocalUniqueDataProvider<PrincessBride>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
