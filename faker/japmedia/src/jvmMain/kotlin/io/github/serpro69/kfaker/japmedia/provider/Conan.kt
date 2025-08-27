package io.github.serpro69.kfaker.japmedia.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.CONAN] category. */
@Suppress("unused")
class Conan internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Conan>(fakerService) {
    override val yamlCategory = YamlCategory.CONAN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Conan>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")

    fun gadgets(): String = resolve("gadgets")

    fun vehicles(): String = resolve("vehicles")
}
