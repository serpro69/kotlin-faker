@file:Suppress("unused")

package io.github.serpro69.kfaker.pictures.provider

import io.github.serpro69.kfaker.RandomService
import io.github.serpro69.kfaker.pictures.convertTo32BitRGBA
import io.github.serpro69.kfaker.provider.FakeDataProvider

/**
 * [FakeDataProvider] implementation for AI-generated [UiFaces](https://uifaces.co/#browse-avatars) avatars.
 */
class UiFaces internal constructor(
    private val randomService: RandomService
) : FakeDataProvider {
    fun avatar(type: UiFacesAvatarType? = null): ByteArray {
        val t = type ?: randomService.nextEnum()
        val n = randomService.nextInt(1, t.size)
        val instr = requireNotNull(javaClass.classLoader.getResourceAsStream("uifaces/$t/$n.jpg")) {
            "UiFaces avatar $t/$n.jpg does not exist"
        }
        return convertTo32BitRGBA(instr.readBytes())
    }
}

enum class UiFacesAvatarType(val size: Int) {
    ABSTRACT(51),
    ALIEN(18),
    CARTOON(32),
    HUMAN(78),
    ROBOT(15),
    ;

    override fun toString(): String = name.lowercase()
}
