package io.github.serpro69.kfaker.dictionary

import java.util.*

/**
 * Represents a map of yaml-based data categories (i.e. `address`, `book`, etc.) to their values.
 */
internal typealias Dictionary = EnumMap<YamlCategory, YamlCategoryData>

/**
 * Represents a dictionary data map of a given category (i.e. `address`)
 * where the `key` is a "function name" (i.e. `address.city`).
 */
internal typealias YamlCategoryData = Map<out String, Any>
