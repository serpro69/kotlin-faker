package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MILITARY] category.
 */
@Suppress("unused")
class Military internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Military>(fakerService) {
    override val categoryName = CategoryName.MILITARY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val armyRank = resolve("army_rank")
    val marinesRank = resolve("marines_rank")
    val navyRank = resolve("navy_rank")
    val airForceRank = resolve("air_force_rank")
    val dodPaygrade = resolve("dod_paygrade")
}
