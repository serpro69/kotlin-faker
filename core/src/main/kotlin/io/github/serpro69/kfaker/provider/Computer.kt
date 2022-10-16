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

    val os by lazy { ComputerOS(fakerService) }

    fun type() = resolve("type")
    fun platform() = resolve("platform")

    @Deprecated(
        message = "This is deprecated and will be removed in future releases",
        replaceWith = ReplaceWith("os.linus()"),
        level = DeprecationLevel.WARNING
    )
    fun linux() = resolve("os", "linux")

    @Deprecated(
        message = "This is deprecated and will be removed in future releases",
        replaceWith = ReplaceWith("os.macOS()"),
        level = DeprecationLevel.WARNING
    )
    fun macOS() = resolve("os", "macos")

    @Deprecated(
        message = "This is deprecated and will be removed in future releases",
        replaceWith = ReplaceWith("os.windows()"),
        level = DeprecationLevel.WARNING
    )
    fun windows() = resolve("os", "windows")
}

@Suppress("unused")
class ComputerOS internal constructor(fakerService: FakerService) : YamlFakeDataProvider<ComputerOS>(fakerService) {
    override val yamlCategory = YamlCategory.COMPUTER
    override val localUniqueDataProvider = LocalUniqueDataProvider<ComputerOS>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun linux() = resolve("os", "linux")
    fun openBsd() = resolve("os", "obenbsd")
    fun templeOS() = resolve("os", "templeos")
    fun plan9() = resolve("os", "plan 9")
    fun macOS() = resolve("os", "macos")
    fun windows() = resolve("os", "windows")
}
