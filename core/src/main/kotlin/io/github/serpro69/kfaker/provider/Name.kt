package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.NAME] category.
 */
@Suppress("unused")
class Name internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Name>(fakerService) {
    override val yamlCategory = YamlCategory.NAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<Name>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(category)
    }

    fun maleFirstName() = resolve("male_first_name")
    fun femaleFirstName() = resolve("female_first_name")
    fun neutralFirstName() = resolve("neutral_first_name")
    fun firstName() = resolve("first_name")
    fun lastName() = resolve("last_name")
    fun name() = resolve("name")
    fun nameWithMiddle() = resolve("name_with_middle")
    // NB! These are not part of original name.yml
    // but are here to support some discrepancies with some localized dicts ('bg', 'ru', 'uk')
    // See also https://github.com/serpro69/kotlin-faker/issues/89
    fun maleLastName() = resolve("male_last_name")
    fun femaleLastName() = resolve("female_last_name")
}
