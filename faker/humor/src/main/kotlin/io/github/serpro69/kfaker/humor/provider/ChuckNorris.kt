package io.github.serpro69.kfaker.humor.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CHUCK_NORRIS] category.
 */
@Suppress("unused")
class ChuckNorris internal constructor(fakerService: FakerService) : YamlFakeDataProvider<ChuckNorris>(fakerService) {
    override val yamlCategory = YamlCategory.CHUCK_NORRIS
    override val localUniqueDataProvider = LocalUniqueDataProvider<ChuckNorris>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun fact() = resolve("fact")
}
