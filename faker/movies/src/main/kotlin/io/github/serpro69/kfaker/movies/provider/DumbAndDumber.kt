package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DUMB_AND_DUMBER] category.
 */
@Suppress("unused")
class DumbAndDumber internal constructor(fakerService: FakerService) : YamlFakeDataProvider<DumbAndDumber>(fakerService) {
    override val yamlCategory = YamlCategory.DUMB_AND_DUMBER
    override val localUniqueDataProvider = LocalUniqueDataProvider<DumbAndDumber>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
