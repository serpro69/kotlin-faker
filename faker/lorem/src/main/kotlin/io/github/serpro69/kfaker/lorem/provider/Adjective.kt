package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.ADJECTIVE] category. */
@Suppress("unused")
class Adjective internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Adjective>(fakerService) {
    override val yamlCategory = YamlCategory.ADJECTIVE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Adjective>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun positive() = resolve("positive")

    fun negative() = resolve("negative")
}
