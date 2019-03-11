package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.KPOP] category.
 */
class KPop internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.KPOP

    val firstGroups = resolve { fakerService.resolve(Faker, it, "i_groups") }
    val secondGroups = resolve { fakerService.resolve(Faker, it, "ii_groups") }
    val thirdGroups = resolve { fakerService.resolve(Faker, it, "iii_groups") }
    val girlGroups = resolve { fakerService.resolve(Faker, it, "girl_groups") }
    val boyBands = resolve { fakerService.resolve(Faker, it, "boy_bands") }
    val solo = resolve { fakerService.resolve(Faker, it, "solo") }
}
