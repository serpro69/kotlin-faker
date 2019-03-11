package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PROGRAMMING_LANGUAGE] category.
 */
class ProgrammingLanguage internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.PROGRAMMING_LANGUAGE

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val creator = resolve { fakerService.resolve(Faker, it, "creator") }
}
