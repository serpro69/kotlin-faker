package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.NATO_PHONETIC_ALPHABET] category.
 */
@Suppress("unused")
class NatoPhoneticAlphabet internal constructor(fakerService: FakerService) : YamlFakeDataProvider<NatoPhoneticAlphabet>(fakerService) {
    override val yamlCategory = YamlCategory.NATO_PHONETIC_ALPHABET
    override val localUniqueDataProvider = LocalUniqueDataProvider<NatoPhoneticAlphabet>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun codeWord() = resolve("code_word")
}
