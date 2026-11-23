package io.github.serpro69.kfaker.runtime

/** Raw values for common components; locale normalization and stream creation belong to callers. */
internal class GenerationConfiguration(val locale: String, val randomSeed: Long?)

/**
 * Looks up one source/category projection without locale fallback or decoding.
 *
 * `sourcePath` identifies an authoring file relative to the locales directory. A null `category`
 * selects non-faker metadata. Missing projections return null for the resolver to report.
 */
internal fun interface DictionaryResources {
    fun projection(sourcePath: String, category: String?): String?
}

/**
 * Common selection boundary; implementations require a positive bound and return 0 <= value < bound.
 */
internal fun interface RandomOperations {
    fun nextInt(bound: Int): Int
}
