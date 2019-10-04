package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.TEAM] category.
 */
@Suppress("unused")
class Team internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Team>(fakerService) {
    override val categoryName = CategoryName.TEAM
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val name = resolve("name")
    val sport = resolve("sport")
    val mascot = resolve("mascot")
}
