package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
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
