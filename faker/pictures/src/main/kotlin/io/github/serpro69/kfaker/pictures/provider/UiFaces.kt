@file:Suppress("unused")

package io.github.serpro69.kfaker.pictures.provider

import io.github.serpro69.kfaker.RandomService
import io.github.serpro69.kfaker.pictures.convertTo32BitRGBA
import io.github.serpro69.kfaker.pictures.provider.UiFacesAvatarGender.FEMALE
import io.github.serpro69.kfaker.provider.FakeDataProvider

/**
 * [FakeDataProvider] implementation for AI-generated [UiFaces](https://uifaces.co/#browse-avatars)
 * avatars.
 */
class UiFaces internal constructor(private val randomService: RandomService) : FakeDataProvider {
    fun avatar(type: UiFacesAvatarType? = null, gender: UiFacesAvatarGender? = null): ByteArray {
        val t = type ?: randomService.nextEnum()
        val g = gender ?: randomService.nextEnum()
        val n = randomService.randomValue(if (g == FEMALE) t.f else t.m)
        val instr =
            requireNotNull(javaClass.classLoader.getResourceAsStream("uifaces/$t/$g/$n.jpg")) {
                "UiFaces avatar $t/$g/$n.jpg does not exist"
            }
        return convertTo32BitRGBA(instr.readBytes())
    }
}

enum class UiFacesAvatarGender {
    FEMALE,
    MALE;

    override fun toString(): String = name.lowercase()
}

enum class UiFacesAvatarType(
    internal val f: List<Int> = emptyList(),
    internal val m: List<Int> = emptyList(),
) {
    ABSTRACT(
        listOf(
            1,
            5,
            6,
            8,
            12,
            15,
            16,
            23,
            26,
            28,
            29,
            30,
            31,
            32,
            33,
            34,
            35,
            36,
            37,
            38,
            39,
            40,
            41,
            42,
        ),
        listOf(
            2,
            3,
            4,
            7,
            9,
            10,
            11,
            13,
            14,
            17,
            18,
            19,
            20,
            21,
            22,
            24,
            25,
            27,
            43,
            44,
            45,
            46,
            47,
            48,
            49,
            50,
            51,
        ),
    ),
    ALIEN(listOf(1, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 16, 18), listOf(2, 7, 14, 15, 17)),
    CARTOON(
        listOf(2, 4, 6, 7, 8, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 27, 29, 32),
        listOf(3, 5, 9, 10, 11, 25, 26, 28, 30, 31),
    ),
    HUMAN(
        listOf(
            1,
            6,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            15,
            16,
            17,
            26,
            28,
            32,
            33,
            34,
            35,
            36,
            38,
            40,
            43,
            44,
            47,
            48,
            49,
            51,
            52,
            55,
            56,
            64,
            66,
            67,
            68,
            69,
            71,
            74,
            76,
            78,
        ),
        listOf(
            2,
            3,
            4,
            5,
            7,
            18,
            19,
            20,
            21,
            22,
            23,
            24,
            25,
            27,
            29,
            30,
            31,
            37,
            39,
            41,
            42,
            45,
            46,
            50,
            53,
            54,
            57,
            58,
            59,
            60,
            61,
            62,
            63,
            65,
            70,
            72,
            73,
            75,
            77,
        ),
    ),
    ROBOT(
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
    );

    override fun toString(): String = name.lowercase()
}
