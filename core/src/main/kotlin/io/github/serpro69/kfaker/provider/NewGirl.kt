package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.NEW_GIRL] category.
 */
@Suppress("unused")
class NewGirl internal constructor(fakerService: FakerService) : YamlFakeDataProvider<NewGirl>(fakerService) {
    override val yamlCategory = YamlCategory.NEW_GIRL
    override val localUniqueDataProvider = LocalUniqueDataProvider<NewGirl>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
