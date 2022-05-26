package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DEPARTED] category.
 */
@Suppress("unused")
class Departed internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Departed>(fakerService) {
    override val yamlCategory = YamlCategory.DEPARTED
    override val localUniqueDataProvider = LocalUniqueDataProvider<Departed>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
