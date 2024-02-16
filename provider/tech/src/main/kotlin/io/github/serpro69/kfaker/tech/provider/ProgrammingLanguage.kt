package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PROGRAMMING_LANGUAGE] category.
 */
@Suppress("unused")
class ProgrammingLanguage internal constructor(fakerService: FakerService) : YamlFakeDataProvider<ProgrammingLanguage>(fakerService) {
    override val yamlCategory = YamlCategory.PROGRAMMING_LANGUAGE
    override val localUniqueDataProvider = LocalUniqueDataProvider<ProgrammingLanguage>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun name() = resolve("name")
    fun creator() = resolve("creator")
}
