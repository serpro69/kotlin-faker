package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.NAME] category. */
@Suppress("unused")
class Name internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Name>(fakerService) {
    override val yamlCategory = YamlCategory.NAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<Name>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

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
}
