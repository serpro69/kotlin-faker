package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BUFFY] category.
 */
@Suppress("unused")
class Buffy internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Buffy>(fakerService) {
    override val yamlCategory = YamlCategory.BUFFY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Buffy>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
    fun actors() = resolve("actors")
    @Deprecated(
        message = "Renamed upstream and will be removed in future release.",
        replaceWith = ReplaceWith("actors()"),
        DeprecationLevel.WARNING
    )
    fun celebrities() = resolve("actors")
    fun bigBads() = resolve("big_bads")
    fun episodes() = resolve("episodes")
}
