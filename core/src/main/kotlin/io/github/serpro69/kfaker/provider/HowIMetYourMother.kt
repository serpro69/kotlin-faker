package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOW_I_MET_YOUR_MOTHER] category.
 */
@Suppress("unused")
class HowIMetYourMother internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HowIMetYourMother>(fakerService) {
    override val category = YamlCategory.HOW_I_MET_YOUR_MOTHER
    override val localUniqueDataProvider = LocalUniqueDataProvider<HowIMetYourMother>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun catchPhrase() = resolve("catch_phrase")
    fun highFive() = resolve("high_five")
    fun quote() = resolve("quote")
}
