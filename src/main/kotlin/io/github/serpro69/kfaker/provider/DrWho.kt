package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DR_WHO] category.
 */
@Suppress("unused")
class DrWho internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DrWho>(fakerService) {
    override val categoryName = CategoryName.DR_WHO

    fun character() = resolve("character")
    fun theDoctors() = resolve("the_doctors")
    fun actors() = resolve("actors")
    fun catchPhrases() = resolve("catch_phrases")
    fun quotes() = resolve("quotes")
    fun villians() = resolve("villians")
    fun species() = resolve("species")
}
