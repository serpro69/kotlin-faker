package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.KPOP] category.
 */
class KPop internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.KPOP

    val firstGroups = resolve { fakerService.resolve(it, "i_groups") }
    val secondGroups = resolve { fakerService.resolve(it, "ii_groups") }
    val thirdGroups = resolve { fakerService.resolve(it, "iii_groups") }
    val girlGroups = resolve { fakerService.resolve(it, "girl_groups") }
    val boyBands = resolve { fakerService.resolve(it, "boy_bands") }
    val solo = resolve { fakerService.resolve(it, "solo") }
}
