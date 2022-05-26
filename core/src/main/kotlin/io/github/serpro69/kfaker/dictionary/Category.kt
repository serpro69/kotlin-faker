package io.github.serpro69.kfaker.dictionary

/**
 * A generic data category of a given [name]
 *
 * @property name the name of this data category
 */
interface Category {
    val name: String

    companion object {

        /**
         * Creates a new [Category] with the given [name].
         */
        internal fun ofName(name: String): Category = object : Category {
            override val name: String = name.uppercase()
        }
    }
}

/**
 * Returns the lowercase [Category.name] as string.
 */
internal fun Category.lowercase(): String = name.lowercase()

