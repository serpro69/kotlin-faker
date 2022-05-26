package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BOJACK_HORSEMAN] category.
 */
@Suppress("unused")
class BojackHorseman internal constructor(fakerService: FakerService) : YamlFakeDataProvider<BojackHorseman>(fakerService) {
    override val yamlCategory = YamlCategory.BOJACK_HORSEMAN
    override val localUniqueDataProvider = LocalUniqueDataProvider<BojackHorseman>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
    fun tongueTwisters() = resolve("tongue_twisters")
}
