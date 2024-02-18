package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FRESH_PRINCE_OF_BEL_AIR] category.
 */
@Suppress("unused")
class FreshPriceOfBelAir internal constructor(fakerService: FakerService) : YamlFakeDataProvider<FreshPriceOfBelAir>(fakerService) {
    override val yamlCategory = YamlCategory.FRESH_PRINCE_OF_BEL_AIR
    override val localUniqueDataProvider = LocalUniqueDataProvider<FreshPriceOfBelAir>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun actors() = resolve("actors")
    @Deprecated(
        message = "Renamed upstream and will be removed in future release.",
        replaceWith = ReplaceWith("actors()"),
        DeprecationLevel.WARNING
    )
    fun celebrities() = resolve("actors")
    fun quotes() = resolve("quotes")
}
