package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MILITARY] category.
 */
@Suppress("unused")
class Military internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Military>(fakerService) {
    override val categoryName = CategoryName.MILITARY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Military>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun armyRank() = resolve("army_rank")
    fun marinesRank() = resolve("marines_rank")
    fun navyRank() = resolve("navy_rank")
    fun airForceRank() = resolve("air_force_rank")
    fun dodPaygrade() = resolve("dod_paygrade")
}
