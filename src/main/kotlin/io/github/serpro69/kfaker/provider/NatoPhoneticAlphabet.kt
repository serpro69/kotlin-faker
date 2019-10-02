package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NATO_PHONETIC_ALPHABET] category.
 */
class NatoPhoneticAlphabet internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.NATO_PHONETIC_ALPHABET

    val codeWord = resolve { fakerService.resolve(it, "code_word") }
}
