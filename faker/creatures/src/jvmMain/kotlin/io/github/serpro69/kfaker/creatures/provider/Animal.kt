package io.github.serpro69.kfaker.creatures.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.CREATURE] category. */
@Suppress("unused")
class Animal internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Animal>(fakerService) {
    override val yamlCategory = YamlCategory.CREATURE
    override val secondaryCategory: Category = Category.ofName("ANIMAL")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Animal>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun name() = resolve(secondaryCategory, "name")
}
