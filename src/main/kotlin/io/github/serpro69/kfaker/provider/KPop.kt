package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.KPOP] category.
 */
@Suppress("unused")
class KPop internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<KPop>(fakerService) {
    override val categoryName = CategoryName.KPOP
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val firstGroups = resolve("i_groups")
    val secondGroups = resolve("ii_groups")
    val thirdGroups = resolve("iii_groups")
    val girlGroups = resolve("girl_groups")
    val boyBands = resolve("boy_bands")
    val solo = resolve("solo")
}
