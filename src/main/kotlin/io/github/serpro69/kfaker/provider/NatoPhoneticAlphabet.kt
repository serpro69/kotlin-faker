package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NATO_PHONETIC_ALPHABET] category.
 */
@Suppress("unused")
class NatoPhoneticAlphabet internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<NatoPhoneticAlphabet>(fakerService) {
    override val categoryName = CategoryName.NATO_PHONETIC_ALPHABET
    override val uniqueDataProvider = UniqueDataProvider<NatoPhoneticAlphabet>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun codeWord() = resolve("code_word")
}
