package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MILITARY] category.
 */
@Suppress("unused")
class Military internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.MILITARY

    val armyRank = resolve { fakerService.resolve(it, "army_rank") }
    val marinesRank = resolve { fakerService.resolve(it, "marines_rank") }
    val navyRank = resolve { fakerService.resolve(it, "navy_rank") }
    val airForceRank = resolve { fakerService.resolve(it, "air_force_rank") }
    val dodPaygrade = resolve { fakerService.resolve(it, "dod_paygrade") }
}
