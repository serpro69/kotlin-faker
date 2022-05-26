package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.THE_IT_CROWD] category.
 */
@Suppress("unused")
class TheITCrowd internal constructor(fakerService: FakerService) : YamlFakeDataProvider<TheITCrowd>(fakerService) {
    override val yamlCategory = YamlCategory.THE_IT_CROWD
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheITCrowd>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun actors() = resolve("actors")
    fun characters() = resolve("characters")
    fun emails() = resolve("emails")
    fun quotes() = resolve("quotes")
}
