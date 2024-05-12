package io.github.serpro69.kfaker.provider.misc

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.provider.AbstractFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * Provides functions for generating random hash sum strings.
 */
class CryptographyProvider internal constructor(
    fakerService: FakerService
) : AbstractFakeDataProvider<CryptographyProvider>(fakerService) {
    override val category: Category = Category.ofName("CRYPTOGRAPHY")
    override val localUniqueDataProvider = LocalUniqueDataProvider<CryptographyProvider>()
    override val unique: CryptographyProvider by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    /**
     * Generates and returns a pseudo-randomly generated md5 hash value, 32 digits long.
     */
    fun md5(): String = sha(32)

    /**
     * Generates and returns a pseudo-randomly generated sha-1 hash value, 40 digits long.
     */
    fun sha1(): String = sha(40)

    /**
     * Generates and returns a pseudo-randomly generated sha-224 hash value, 56 digits long.
     */
    fun sha224(): String = sha(56)

    /**
     * Generates and returns a pseudo-randomly generated sha-256 hash value, 64 digits long.
     */
    fun sha256(): String = sha(64)

    /**
     * Generates and returns a pseudo-randomly generated sha-384 hash value, 96 digits long.
     */
    fun sha384(): String = sha(96)

    /**
     * Generates and returns a pseudo-randomly generated sha-512 hash value, 128 digits long.
     */
    fun sha512(): String = sha(128)

    private fun sha(len: Int): String = with(fakerService) {
        resolveUniqueValue("regexify") { """[a-f0-9]{$len}""".regexify() }
    }
}
