package io.github.serpro69.kfaker.creatures.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CREATURE] category.
 */
@Suppress("unused")
class Horse internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Horse>(fakerService) {
    override val yamlCategory = YamlCategory.CREATURE
    override val secondaryCategory: Category = Category.ofName("HORSE")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Horse>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun name() = resolve(secondaryCategory, "name")
    fun breed() = resolve(secondaryCategory, "breed")
}
