package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DR_WHO] category.
 */
@Suppress("unused")
class DrWho internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DrWho>(fakerService) {
    override val categoryName = CategoryName.DR_WHO
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val character = resolve("character")
    val theDoctors = resolve("the_doctors")
    val actors = resolve("actors")
    val catchPhrases = resolve("catch_phrases")
    val quotes = resolve("quotes")
    val villians = resolve("villians")
    val species = resolve("species")
}
