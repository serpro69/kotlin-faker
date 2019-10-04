package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ONE_PIECE] category.
 */
@Suppress("unused")
class OnePiece internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<OnePiece>(fakerService) {
    override val categoryName = CategoryName.ONE_PIECE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val seas = resolve("seas")
    val islands = resolve("islands")
    val locations = resolve("locations")
    val quotes = resolve("quotes")
    val akumasNoMi = resolve("akumas_no_mi")
}
