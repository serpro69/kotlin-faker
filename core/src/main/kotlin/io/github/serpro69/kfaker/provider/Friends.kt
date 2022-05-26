package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FRIENDS] category.
 */
@Suppress("unused")
class Friends internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Friends>(fakerService) {
    override val yamlCategory = YamlCategory.FRIENDS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Friends>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
}
