package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DR_WHO] category.
 */
@Suppress("unused")
class DrWho internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DrWho>(fakerService) {
    override val categoryName = CategoryName.DR_WHO
    override val localUniqueDataProvider = LocalUniqueDataProvider<DrWho>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun theDoctors() = resolve("the_doctors")
    fun actors() = resolve("actors")
    fun catchPhrases() = resolve("catch_phrases")
    fun quotes() = resolve("quotes")
    fun villains() = resolve("villains")
    fun species() = resolve("species")
}
