package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ONE_PIECE] category.
 */
@Suppress("unused")
class OnePiece internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ONE_PIECE

    val characters = resolve { fakerService.resolve(it, "characters") }
    val seas = resolve { fakerService.resolve(it, "seas") }
    val islands = resolve { fakerService.resolve(it, "islands") }
    val locations = resolve { fakerService.resolve(it, "locations") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
    val akumasNoMi = resolve { fakerService.resolve(it, "akumas_no_mi") }
}
