@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.KAMEN_RIDER] category.
 */
class KamenRider internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<KamenRider>(fakerService) {
    override val category = YamlCategory.KAMEN_RIDER
    override val localUniqueDataProvider = LocalUniqueDataProvider<KamenRider>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    val showa = KamenRiderShowa(fakerService)
    val heisei = KamenRiderHeisei(fakerService)
    val reiwa = KamenRiderReiwa(fakerService)
}

class KamenRiderShowa internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<KamenRiderShowa>(fakerService) {
    override val category = YamlCategory.KAMEN_RIDER
    override val localUniqueDataProvider = LocalUniqueDataProvider<KamenRiderShowa>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun series(): String = resolve("showa", "series")
    fun kamenRiders(): String = resolve("showa", "kamen_riders")
    fun users(): String = resolve("showa", "users")
    fun transformationDevices(): String = resolve("showa", "transformation_devices")
}

class KamenRiderHeisei internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<KamenRiderHeisei>(fakerService) {
    override val category = YamlCategory.KAMEN_RIDER
    override val localUniqueDataProvider = LocalUniqueDataProvider<KamenRiderHeisei>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun series(): String = resolve("heisei", "series")
    fun kamenRiders(): String = resolve("heisei", "kamen_riders")
    fun users(): String = resolve("heisei", "users")
    fun collectibleDevices(): String = resolve("heisei", "collectible_devices")
    fun transformationDevices(): String = resolve("heisei", "transformation_devices")
}

class KamenRiderReiwa internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<KamenRiderReiwa>(fakerService) {
    override val category = YamlCategory.KAMEN_RIDER
    override val localUniqueDataProvider = LocalUniqueDataProvider<KamenRiderReiwa>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun series(): String = resolve("reiwa", "series")
    fun kamenRiders(): String = resolve("reiwa", "kamen_riders")
    fun users(): String = resolve("reiwa", "users")
    fun collectibleDevices(): String = resolve("reiwa", "collectible_devices")
    fun transformationDevices(): String = resolve("reiwa", "transformation_devices")
}
