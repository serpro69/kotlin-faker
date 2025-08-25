package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.BUFFY] category. */
@Suppress("unused")
class Buffy internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Buffy>(fakerService) {
    override val yamlCategory = YamlCategory.BUFFY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Buffy>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun quotes() = resolve("quotes")

    fun actors() = resolve("actors")

    fun bigBads() = resolve("big_bads")

    fun episodes() = resolve("episodes")
}
