package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TEAM] category.
 */
@Suppress("unused")
class Team internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Team>(fakerService) {
    override val yamlCategory = YamlCategory.TEAM
    override val localUniqueDataProvider = LocalUniqueDataProvider<Team>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun name() = resolve("name")
    fun sport() = resolve("sport")
    fun mascot() = resolve("mascot")
}
