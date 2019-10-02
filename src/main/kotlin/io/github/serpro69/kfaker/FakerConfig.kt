@file:JvmName("FakerConfigBuilder")
package io.github.serpro69.kfaker

import java.util.*

class FakerConfig private constructor(
    val locale: String,
    val random: Random
) {

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder internal constructor() {
        var locale = "en"
        var random = Random()

        internal fun build() = FakerConfig(
            locale,
            random
        )
    }
}

fun FakerConfig.Builder.create(block: FakerConfig.Builder.() -> Unit) = this.apply(block).build()