package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HACKERS] category.
 */
@Suppress("unused")
class Hackers internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Hackers>(fakerService) {
    override val yamlCategory = YamlCategory.HACKERS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hackers>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun handles() = resolve("handles")
    fun quotes() = resolve("quotes")
}
