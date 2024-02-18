package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HACKERS] category.
 */
@Suppress("unused")
class Hackers internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Hackers>(fakerService) {
    override val yamlCategory = YamlCategory.HACKERS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Hackers>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun handles() = resolve("handles")
    fun quotes() = resolve("quotes")
}
