package io.github.serpro69.kfaker.misc.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.MILITARY] category.
 */
@Suppress("unused")
class Military internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Military>(fakerService) {
    override val yamlCategory = YamlCategory.MILITARY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Military>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun armyRank() = resolve("army_rank")
    fun marinesRank() = resolve("marines_rank")
    fun navyRank() = resolve("navy_rank")
    fun coastGuardRank() = resolve("coast_guard_rank")
    fun airForceRank() = resolve("air_force_rank")
    fun spaceForceRank() = resolve("space_force_rank")
    fun dodPaygrade() = resolve("dod_paygrade")
}
