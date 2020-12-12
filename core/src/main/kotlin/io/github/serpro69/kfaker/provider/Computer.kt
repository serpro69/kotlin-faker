package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.ANCIENT] category.
 */
@Suppress("unused")
class Computer internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Computer>(fakerService) {
    override val categoryName = CategoryName.COMPUTER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Computer>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun type() = resolve("type")
    fun platform() = resolve("platform")
    fun linux() = resolve("os", "linux")
    fun macOS() = resolve("os", "macos")
    fun windows() = resolve("os", "windows")
}
