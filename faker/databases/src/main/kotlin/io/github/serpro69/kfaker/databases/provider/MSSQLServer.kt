package io.github.serpro69.kfaker.databases.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DATABASES] category.
 */
@Suppress("unused")
class MSSQLServer internal constructor(fakerService: FakerService) : YamlFakeDataProvider<MSSQLServer>(fakerService) {
    override val yamlCategory = YamlCategory.DATABASES
    override val secondaryCategory: Category = Category.ofName("MS_SQL_SERVER")
    override val localUniqueDataProvider = LocalUniqueDataProvider<MSSQLServer>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun type() = resolve(secondaryCategory, "type")
}