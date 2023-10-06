package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.QUOTE] category.
 */
@Suppress("unused")
class MitchHedberg internal constructor(fakerService: FakerService) : YamlFakeDataProvider<MitchHedberg>(fakerService) {
    override val yamlCategory = YamlCategory.QUOTE
    override val secondaryCategory: Category = Category.ofName("MITCH_HEDBERG")
    override val localUniqueDataProvider = LocalUniqueDataProvider<MitchHedberg>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun quote() = resolve("mitch_hedberg")
}
