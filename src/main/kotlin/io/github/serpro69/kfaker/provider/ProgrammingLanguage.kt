package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.PROGRAMMING_LANGUAGE] category.
 */
class ProgrammingLanguage internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PROGRAMMING_LANGUAGE

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val creator = resolve { fakerService.resolve(Faker, it, "creator") }
}
