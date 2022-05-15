@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CREATURE] category.
 */
class Bird internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Bird>(fakerService) {
    override val category = YamlCategory.CREATURE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Bird>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    val orderCommonMap = BirdOrderCommonMap(fakerService)

    fun anatomy(): String = resolve("bird", "anatomy")
    fun anatomyPastTense(): String = resolve("bird", "anatomy_past_tense")
    fun geo(): String = resolve("bird", "geo")
    fun colors(): String = resolve("bird", "colors")
    fun emotionalAdjectives(): String = resolve("bird", "emotional_adjectives")
    fun sillyAdjectives(): String = resolve("bird", "silly_adjectives")
    fun adjectives(): String = resolve("bird", "adjectives")
    fun plausibleCommonNames(): String = resolve("bird", "plausible_common_names")
    fun implausibleCommonNames(): String = resolve("bird", "implausible_common_names")
    fun commonFamilyName(): String = resolve("bird", "common_family_name")
}

class BirdOrderCommonMap internal constructor(
    fakerService: FakerService
) : AbstractFakeDataProvider<BirdOrderCommonMap>(fakerService) {
    override val category = YamlCategory.CREATURE
    override val localUniqueDataProvider = LocalUniqueDataProvider<BirdOrderCommonMap>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun accipitriformes(): String = resolve("bird", "order_common_map", "Accipitriformes")
    fun anseriformes(): String = resolve("bird", "order_common_map", "Anseriformes")
    fun apterygiformes(): String = resolve("bird", "order_common_map", "Apterygiformes")
    fun bucerotiformes(): String = resolve("bird", "order_common_map", "Bucerotiformes")
    fun caprimulgiformes(): String = resolve("bird", "order_common_map", "Caprimulgiformes")
    fun cariamiformes(): String = resolve("bird", "order_common_map", "Cariamiformes")
    fun casuariiformes(): String = resolve("bird", "order_common_map", "Casuariiformes")
    fun cathartiformes(): String = resolve("bird", "order_common_map", "Cathartiformes")
    fun charadriiformes(): String = resolve("bird", "order_common_map", "Charadriiformes")
    fun ciconiiformes(): String = resolve("bird", "order_common_map", "Ciconiiformes")
    fun coliiformes(): String = resolve("bird", "order_common_map", "Coliiformes")
    fun columbiformes(): String = resolve("bird", "order_common_map", "Columbiformes")
    fun coraciiformes(): String = resolve("bird", "order_common_map", "Coraciiformes")
    fun cuculiformes(): String = resolve("bird", "order_common_map", "Cuculiformes")
    fun eurypygiformes(): String = resolve("bird", "order_common_map", "Eurypygiformes")
    fun falconiformes(): String = resolve("bird", "order_common_map", "Falconiformes")
    fun galbuliformes(): String = resolve("bird", "order_common_map", "Galbuliformes")
    fun galliformes(): String = resolve("bird", "order_common_map", "Galliformes")
    fun gaviiformes(): String = resolve("bird", "order_common_map", "Gaviiformes")
    fun gruiformes(): String = resolve("bird", "order_common_map", "Gruiformes")
    fun mesitornithiformes(): String = resolve("bird", "order_common_map", "Mesitornithiformes")
    fun musophagiformes(): String = resolve("bird", "order_common_map", "Musophagiformes")
    fun opisthocomiformes(): String = resolve("bird", "order_common_map", "Opisthocomiformes")
    fun otidiformes(): String = resolve("bird", "order_common_map", "Otidiformes")
    fun passeriformes(): String = resolve("bird", "order_common_map", "Passeriformes")
    fun pelecaniformes(): String = resolve("bird", "order_common_map", "Pelecaniformes")
    fun phaethontiformes(): String = resolve("bird", "order_common_map", "Phaethontiformes")
    fun phoenicopteriformes(): String = resolve("bird", "order_common_map", "Phoenicopteriformes")
    fun piciformes(): String = resolve("bird", "order_common_map", "Piciformes")
    fun podicipediformes(): String = resolve("bird", "order_common_map", "Podicipediformes")
    fun procellariiformes(): String = resolve("bird", "order_common_map", "Procellariiformes")
    fun psittaciformes(): String = resolve("bird", "order_common_map", "Psittaciformes")
    fun pterocliformes(): String = resolve("bird", "order_common_map", "Pterocliformes")
    fun rheiformes(): String = resolve("bird", "order_common_map", "Rheiformes")
    fun sphenisciformes(): String = resolve("bird", "order_common_map", "Sphenisciformes")
    fun strigiformes(): String = resolve("bird", "order_common_map", "Strigiformes")
    fun struthioniformes(): String = resolve("bird", "order_common_map", "Struthioniformes")
    fun suliformes(): String = resolve("bird", "order_common_map", "Suliformes")
    fun tinamiformes(): String = resolve("bird", "order_common_map", "Tinamiformes")
    fun trogoniformes(): String = resolve("bird", "order_common_map", "Trogoniformes")
}

