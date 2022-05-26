package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CREATURE] category.
 */
@Suppress("unused")
class Dog internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Dog>(fakerService) {
    override val yamlCategory = YamlCategory.CREATURE
    override val secondaryCategory: Category = Category.ofName("DOG")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dog>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun name() = resolve("dog", "name")
    fun breed() = resolve("dog", "breed")
    fun sound() = resolve("dog", "sound")
    fun memePhrase() = resolve("dog", "meme_phrase")
    fun age() = resolve("dog", "age")
    fun coatLength() = resolve("dog", "coat_length")
    fun size() = resolve("dog", "size")
}
