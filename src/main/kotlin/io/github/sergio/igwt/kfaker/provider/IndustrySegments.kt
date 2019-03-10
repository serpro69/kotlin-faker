package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
