package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ANCIENT] category.
 */
@Suppress("unused")
class Computer internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Computer>(fakerService) {
    override val yamlCategory = YamlCategory.COMPUTER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Computer>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun type() = resolve("type")
    fun platform() = resolve("platform")
    fun linux() = resolve("os", "linux")
    fun macOS() = resolve("os", "macos")
    fun windows() = resolve("os", "windows")
}
