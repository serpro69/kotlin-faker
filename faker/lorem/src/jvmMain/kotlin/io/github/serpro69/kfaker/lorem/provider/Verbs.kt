package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.VERBS] category. */
@Suppress("unused")
class Verbs internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Verbs>(fakerService) {
    override val yamlCategory = YamlCategory.VERBS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Verbs>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun base() = resolve("base")

    fun past() = resolve("past")

    fun pastParticiple() = resolve("past_participle")

    fun simplePresent() = resolve("simple_present")

    fun ingForm() = resolve("ing_form")
}
