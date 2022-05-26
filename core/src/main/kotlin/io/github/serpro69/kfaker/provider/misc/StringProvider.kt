package io.github.serpro69.kfaker.provider.misc

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.provider.AbstractFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * Provides functions for generating values from string expressions.
 */
class StringProvider internal constructor(
    fakerService: FakerService
) : AbstractFakeDataProvider<StringProvider>(fakerService) {
    override val category: Category = object : Category {
        override val name: String = "STRING_PROVIDER"
    }
    override val localUniqueDataProvider = LocalUniqueDataProvider<StringProvider>()
    override val unique: StringProvider by UniqueProviderDelegate(localUniqueDataProvider)

    /**
     * Replaces every `#` char for this [template] string with a random int from 0 to 9 inclusive,
     * and returns the modified [String].
     */
    fun numerify(template: String) = with(fakerService) { resolveUniqueValue(template.numerify, "numerify") }

    /**
     * Replaces every `?` char for this [template] string with a random letter from the English alphabet,
     * and returns the modified [String].
     *
     * @param upper set to `true` or `false` to control the case of generated letters.
     */
    fun letterify(template: String, upper: Boolean? = null) = with(fakerService) {
        resolveUniqueValue({ template.letterify(upper) }, "letterify")
    }

    /**
     * Combines applies both [numerify] and [letterify] functions to the [template] string,
     * and returns the modified [String].
     *
     * @param upper set to `true` or `false` to control the case of generated letters.
     */
    fun bothify(template: String, upper: Boolean? = null) = with(fakerService) {
        resolveUniqueValue({ template.numerify().letterify(upper) }, "bothify")
    }

    /**
     * Returns a string of generated values based on the regex expressions in the [template] input,
     * for example `regexify("""\d{3}""")` will return a string consisting of 3 random digits.
     */
    fun regexify(template: String) = with(fakerService) {
        resolveUniqueValue(template.generexify, "regexify")
    }
}
