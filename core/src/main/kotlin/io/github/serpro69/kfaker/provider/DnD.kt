package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DND] category.
 */
@Suppress("unused")
class DnD internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DnD>(fakerService) {
    override val categoryName = CategoryName.DND
    override val localUniqueDataProvider = LocalUniqueDataProvider<DnD>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun species() = resolve("species")
    fun klasses() = resolve("klasses")
    fun backgrounds() = resolve("backgrounds")
    fun alignments() = resolve("alignments")
}
