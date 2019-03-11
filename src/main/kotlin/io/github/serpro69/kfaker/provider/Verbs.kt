package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.VERBS] category.
 */
class Verbs internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.VERBS

    val base = resolve { fakerService.resolve(Faker, it, "base") }
    val past = resolve { fakerService.resolve(Faker, it, "past") }
    val pastParticiple = resolve { fakerService.resolve(Faker, it, "past_participle") }
    val simplePresent = resolve { fakerService.resolve(Faker, it, "simple_present") }
    val ingForm = resolve { fakerService.resolve(Faker, it, "ing_form") }
}
