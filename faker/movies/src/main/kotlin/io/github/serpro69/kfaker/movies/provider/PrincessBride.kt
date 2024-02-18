package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PRINCESS_BRIDE] category.
 */
@Suppress("unused")
class PrincessBride internal constructor(fakerService: FakerService) : YamlFakeDataProvider<PrincessBride>(fakerService) {
    override val yamlCategory = YamlCategory.PRINCESS_BRIDE
    override val localUniqueDataProvider = LocalUniqueDataProvider<PrincessBride>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
