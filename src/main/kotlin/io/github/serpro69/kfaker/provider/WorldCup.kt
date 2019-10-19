package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.WORLD_CUP] category.
 */
@Suppress("unused")
class WorldCup internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<WorldCup>(fakerService) {
    override val categoryName = CategoryName.WORLD_CUP
    override val localUniqueDataProvider = LocalUniqueDataProvider<WorldCup>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

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
