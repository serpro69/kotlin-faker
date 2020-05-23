package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.KPOP] category.
 */
@Suppress("unused")
class KPop internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<KPop>(fakerService) {
    override val categoryName = CategoryName.KPOP
    override val localUniqueDataProvider = LocalUniqueDataProvider<KPop>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun firstGroups() = resolve("i_groups")
    fun secondGroups() = resolve("ii_groups")
    fun thirdGroups() = resolve("iii_groups")
    fun girlGroups() = resolve("girl_groups")
    fun boyBands() = resolve("boy_bands")
    fun solo() = resolve("solo")
}
