package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.DR_WHO] category. */
@Suppress("unused")
class DrWho internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<DrWho>(fakerService) {
    override val yamlCategory = YamlCategory.DR_WHO
    override val localUniqueDataProvider = LocalUniqueDataProvider<DrWho>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")

    fun theDoctors() = resolve("the_doctors")

    fun actors() = resolve("actors")

    fun catchPhrases() = resolve("catch_phrases")

    fun quotes() = resolve("quotes")

    fun villains() = resolve("villains")

    fun species() = resolve("species")
}
