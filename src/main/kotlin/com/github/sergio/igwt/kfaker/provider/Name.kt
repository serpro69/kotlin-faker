package com.github.sergio.igwt.kfaker.provider

import com.github.sergio.igwt.kfaker.Faker
import com.github.sergio.igwt.kfaker.FakerService
import com.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for `name` category.
 */
class Name internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.NAME

    val name = resolve { fakerService.resolve(Faker, it, "name") }
}