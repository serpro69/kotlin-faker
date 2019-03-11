package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GREEK_PHILOSOPHERS] category.
 */
class GreekPhilosophers internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GREEK_PHILOSOPHERS

    val names = resolve { fakerService.resolve(Faker, it, "names") }
    val quoetes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
