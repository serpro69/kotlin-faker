package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.NATO_PHONETIC_ALPHABET] category.
 */
@Suppress("unused")
class NatoPhoneticAlphabet internal constructor(fakerService: FakerService) : YamlFakeDataProvider<NatoPhoneticAlphabet>(fakerService) {
    override val yamlCategory = YamlCategory.NATO_PHONETIC_ALPHABET
    override val localUniqueDataProvider = LocalUniqueDataProvider<NatoPhoneticAlphabet>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun codeWord() = resolve("code_word")
}
