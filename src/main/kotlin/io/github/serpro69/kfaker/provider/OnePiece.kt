package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ONE_PIECE] category.
 */
@Suppress("unused")
class OnePiece internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<OnePiece>(fakerService) {
    override val categoryName = CategoryName.ONE_PIECE
    override val localUniqueDataProvider = LocalUniqueDataProvider<OnePiece>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun seas() = resolve("seas")
    fun islands() = resolve("islands")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
    fun akumasNoMi() = resolve("akumas_no_mi")
}
