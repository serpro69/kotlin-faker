package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.NATO_PHONETIC_ALPHABET] category.
 */
class NatoPhoneticAlphabet internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.NATO_PHONETIC_ALPHABET

    val codeWord = resolve { fakerService.resolve(Faker, it, "code_word") }
}
