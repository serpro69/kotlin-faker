package io.github.serpro69.kfaker.databases.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DATABASE] category.
 */
@Suppress("unused")
class OracleDB internal constructor(fakerService: FakerService) : YamlFakeDataProvider<OracleDB>(fakerService) {
    override val yamlCategory = YamlCategory.DATABASE
    override val secondaryCategory: Category = Category.ofName("ORACLEDB")
    override val localUniqueDataProvider = LocalUniqueDataProvider<OracleDB>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun dataType() = resolve(secondaryCategory, "data_type")
}