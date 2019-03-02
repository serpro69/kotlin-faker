package com.github.sergio.igwt.kfaker.provider

import com.github.sergio.igwt.kfaker.Faker
import com.github.sergio.igwt.kfaker.FakerService
import com.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.ANCIENT] category.
 */
class Ancient internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ANCIENT

    val god = resolve { fakerService.resolve(Faker, it, "god") }
    val primordial = resolve { fakerService.resolve(Faker, it, "primordial") }
    val titan = resolve { fakerService.resolve(Faker, it, "titan") }
    val hero = resolve { fakerService.resolve(Faker, it, "hero") }
}
