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

    @Deprecated(
        message = "Deprecated since 1.5.0 due to changes in dict file. Will be removed in 1.6.0",
        replaceWith = ReplaceWith("races()"),
        level = DeprecationLevel.WARNING
    )
    fun species() = races()

    fun alignments() = resolve("alignments")
    fun backgrounds() = resolve("backgrounds")
    fun cities() = resolve("cities")
    fun klasses() = resolve("klasses")
    fun languages() = resolve("languages")
    fun meleeWeapons() = resolve("melee_weapons")
    fun monsters() = resolve("monsters")
    fun races() = resolve("races")
    fun rangedWeapons() = resolve("ranged_weapons")
}
