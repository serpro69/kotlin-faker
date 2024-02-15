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

    fun name() = resolve(secondaryCategory, "name")
    fun breed() = resolve(secondaryCategory, "breed")
    fun sound() = resolve(secondaryCategory, "sound")
    fun memePhrase() = resolve(secondaryCategory, "meme_phrase")
    fun age() = resolve(secondaryCategory, "age")
    fun coatLength() = resolve(secondaryCategory, "coat_length")
    fun size() = resolve(secondaryCategory, "size")
}
