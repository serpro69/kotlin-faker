package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INDUSTRY_SEGMENTS] category.
 */
class IndustrySegments internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.INDUSTRY_SEGMENTS

    val industry = resolve { fakerService.resolve(Faker, it, "industry") }
    val superSector = resolve { fakerService.resolve(Faker, it, "super_sector") }
    val sector = resolve { fakerService.resolve(Faker, it, "sector") }
    val subSector = resolve { fakerService.resolve(Faker, it, "sub_sector") }
}
