package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.TEAM] category.
 */
@Suppress("unused")
class Team internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Team>(fakerService) {
    override val categoryName = CategoryName.TEAM
    override val localUniqueDataProvider = LocalUniqueDataProvider<Team>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
    fun sport() = resolve("sport")
    fun mascot() = resolve("mascot")
}
