package io.github.serpro69.kfaker.sports.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
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
