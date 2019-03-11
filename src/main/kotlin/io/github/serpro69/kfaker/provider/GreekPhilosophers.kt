package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GREEK_PHILOSOPHERS] category.
 */
class GreekPhilosophers internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GREEK_PHILOSOPHERS

    val names = resolve { fakerService.resolve(Faker, it, "names") }
    val quoetes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
