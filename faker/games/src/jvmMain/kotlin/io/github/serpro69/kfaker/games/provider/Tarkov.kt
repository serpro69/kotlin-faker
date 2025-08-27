@file:Suppress("unused")

package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.TARKOV] category. */
class Tarkov internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Tarkov>(fakerService) {
    override val yamlCategory = YamlCategory.TARKOV
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tarkov>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val quests by lazy { TarkovQuests(fakerService) }

    fun locations() = resolve("locations")

    fun traders() = resolve("traders")

    fun weapons() = resolve("weapons")

    fun items() = resolve("items")

    fun factions() = resolve("factions")

    fun bosses() = resolve("bosses")
}

class TarkovQuests internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TarkovQuests>(fakerService) {
    override val yamlCategory = YamlCategory.TARKOV
    override val localUniqueDataProvider = LocalUniqueDataProvider<TarkovQuests>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun prapor() = resolve("quests", "prapor")

    fun therapist() = resolve("quests", "therapist")

    fun skier() = resolve("quests", "skier")

    fun peacekeeper() = resolve("quests", "peacekeeper")

    fun mechanic() = resolve("quests", "mechanic")

    fun ragman() = resolve("quests", "ragman")

    fun jaeger() = resolve("quests", "jaeger")

    fun fence() = resolve("quests", "fence")
}
