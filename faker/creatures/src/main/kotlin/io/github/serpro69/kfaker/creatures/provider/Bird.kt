@file:Suppress("unused")

package io.github.serpro69.kfaker.creatures.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.CREATURE] category. */
class Bird internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Bird>(fakerService) {
    override val yamlCategory = YamlCategory.CREATURE
    override val secondaryCategory: Category = Category.ofName("BIRD")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Bird>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    val orderCommonMap by lazy { BirdOrderCommonMap(fakerService, secondaryCategory) }

    fun anatomy(): String = resolve(secondaryCategory, "anatomy")

    fun anatomyPastTense(): String = resolve(secondaryCategory, "anatomy_past_tense")

    fun geo(): String = resolve(secondaryCategory, "geo")

    fun colors(): String = resolve(secondaryCategory, "colors")

    fun emotionalAdjectives(): String = resolve(secondaryCategory, "emotional_adjectives")

    fun sillyAdjectives(): String = resolve(secondaryCategory, "silly_adjectives")

    fun adjectives(): String = resolve(secondaryCategory, "adjectives")

    fun plausibleCommonNames(): String = resolve(secondaryCategory, "plausible_common_names")

    fun implausibleCommonNames(): String = resolve(secondaryCategory, "implausible_common_names")

    fun commonFamilyName(): String = resolve(secondaryCategory, "common_family_name")
}

class BirdOrderCommonMap
internal constructor(fakerService: FakerService, override val secondaryCategory: Category) :
    YamlFakeDataProvider<BirdOrderCommonMap>(fakerService) {
    override val yamlCategory = YamlCategory.CREATURE
    override val localUniqueDataProvider = LocalUniqueDataProvider<BirdOrderCommonMap>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    fun accipitriformes(): String =
        resolve(secondaryCategory, "order_common_map", "Accipitriformes")

    fun anseriformes(): String = resolve(secondaryCategory, "order_common_map", "Anseriformes")

    fun apterygiformes(): String = resolve(secondaryCategory, "order_common_map", "Apterygiformes")

    fun bucerotiformes(): String = resolve(secondaryCategory, "order_common_map", "Bucerotiformes")

    fun caprimulgiformes(): String =
        resolve(secondaryCategory, "order_common_map", "Caprimulgiformes")

    fun cariamiformes(): String = resolve(secondaryCategory, "order_common_map", "Cariamiformes")

    fun casuariiformes(): String = resolve(secondaryCategory, "order_common_map", "Casuariiformes")

    fun cathartiformes(): String = resolve(secondaryCategory, "order_common_map", "Cathartiformes")

    fun charadriiformes(): String =
        resolve(secondaryCategory, "order_common_map", "Charadriiformes")

    fun ciconiiformes(): String = resolve(secondaryCategory, "order_common_map", "Ciconiiformes")

    fun coliiformes(): String = resolve(secondaryCategory, "order_common_map", "Coliiformes")

    fun columbiformes(): String = resolve(secondaryCategory, "order_common_map", "Columbiformes")

    fun coraciiformes(): String = resolve(secondaryCategory, "order_common_map", "Coraciiformes")

    fun cuculiformes(): String = resolve(secondaryCategory, "order_common_map", "Cuculiformes")

    fun eurypygiformes(): String = resolve(secondaryCategory, "order_common_map", "Eurypygiformes")

    fun falconiformes(): String = resolve(secondaryCategory, "order_common_map", "Falconiformes")

    fun galbuliformes(): String = resolve(secondaryCategory, "order_common_map", "Galbuliformes")

    fun galliformes(): String = resolve(secondaryCategory, "order_common_map", "Galliformes")

    fun gaviiformes(): String = resolve(secondaryCategory, "order_common_map", "Gaviiformes")

    fun gruiformes(): String = resolve(secondaryCategory, "order_common_map", "Gruiformes")

    fun mesitornithiformes(): String =
        resolve(secondaryCategory, "order_common_map", "Mesitornithiformes")

    fun musophagiformes(): String =
        resolve(secondaryCategory, "order_common_map", "Musophagiformes")

    fun opisthocomiformes(): String =
        resolve(secondaryCategory, "order_common_map", "Opisthocomiformes")

    fun otidiformes(): String = resolve(secondaryCategory, "order_common_map", "Otidiformes")

    fun passeriformes(): String = resolve(secondaryCategory, "order_common_map", "Passeriformes")

    fun pelecaniformes(): String = resolve(secondaryCategory, "order_common_map", "Pelecaniformes")

    fun phaethontiformes(): String =
        resolve(secondaryCategory, "order_common_map", "Phaethontiformes")

    fun phoenicopteriformes(): String =
        resolve(secondaryCategory, "order_common_map", "Phoenicopteriformes")

    fun piciformes(): String = resolve(secondaryCategory, "order_common_map", "Piciformes")

    fun podicipediformes(): String =
        resolve(secondaryCategory, "order_common_map", "Podicipediformes")

    fun procellariiformes(): String =
        resolve(secondaryCategory, "order_common_map", "Procellariiformes")

    fun psittaciformes(): String = resolve(secondaryCategory, "order_common_map", "Psittaciformes")

    fun pterocliformes(): String = resolve(secondaryCategory, "order_common_map", "Pterocliformes")

    fun rheiformes(): String = resolve(secondaryCategory, "order_common_map", "Rheiformes")

    fun sphenisciformes(): String =
        resolve(secondaryCategory, "order_common_map", "Sphenisciformes")

    fun strigiformes(): String = resolve(secondaryCategory, "order_common_map", "Strigiformes")

    fun struthioniformes(): String =
        resolve(secondaryCategory, "order_common_map", "Struthioniformes")

    fun suliformes(): String = resolve(secondaryCategory, "order_common_map", "Suliformes")

    fun tinamiformes(): String = resolve(secondaryCategory, "order_common_map", "Tinamiformes")

    fun trogoniformes(): String = resolve(secondaryCategory, "order_common_map", "Trogoniformes")
}
