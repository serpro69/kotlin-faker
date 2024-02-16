package io.github.serpro69.kfaker.sports.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.WORLD_CUP] category.
 */
@Suppress("unused")
class WorldCup internal constructor(fakerService: FakerService) : YamlFakeDataProvider<WorldCup>(fakerService) {
    override val yamlCategory = YamlCategory.WORLD_CUP
    override val localUniqueDataProvider = LocalUniqueDataProvider<WorldCup>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun teams() = resolve("teams")
    fun stadiums() = resolve("stadiums")
    fun cities() = resolve("cities")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    // TODO: 3/10/2019 better resolving of group names
    fun groups(group: String) = resolve("groups", group)

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    // TODO: 3/10/2019 better resoving of rooster for different countries and types
    fun roosters(country: String, type: String) = resolve("roosters", country, type)
}
