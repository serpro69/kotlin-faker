package io.github.serpro69.kfaker.travel.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.NATION] category. */
@Suppress("unused")
class Nation internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Nation>(fakerService) {
    override val yamlCategory = YamlCategory.NATION
    override val localUniqueDataProvider = LocalUniqueDataProvider<Nation>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    // currently not supported due to logic for getting raw value for List<List<*>> types
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun flag() = resolve("flag")

    fun nationality() = resolve("nationality")

    fun language() = resolve("language")

    fun capitalCity() = resolve("capital_city")
}
