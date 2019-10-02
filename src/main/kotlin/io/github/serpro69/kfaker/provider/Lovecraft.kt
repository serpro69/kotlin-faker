package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.LOVECRAFT] category.
 */
class Lovecraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.LOVECRAFT

    val fhtagn = resolve { fakerService.resolve(it, "fhtagn") }
    val deity = resolve { fakerService.resolve(it, "deity") }
    val location = resolve { fakerService.resolve(it, "location") }
    val tome = resolve { fakerService.resolve(it, "tome") }
    val words = resolve { fakerService.resolve(it, "words") }
}
