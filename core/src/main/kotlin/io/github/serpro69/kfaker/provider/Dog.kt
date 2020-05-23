package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
@Suppress("unused")
class Dog internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Dog>(fakerService) {
    override val categoryName = CategoryName.CREATURE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dog>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("dog", "name")
    fun breed() = resolve("dog", "breed")
    fun sound() = resolve("dog", "sound")
    fun memePhrase() = resolve("dog", "meme_phrase")
    fun age() = resolve("dog", "age")
    fun coatLength() = resolve("dog", "coat_length")
    fun size() = resolve("dog", "size")
}
