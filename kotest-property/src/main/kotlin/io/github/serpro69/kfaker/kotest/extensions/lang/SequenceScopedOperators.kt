package io.github.serpro69.kfaker.kotest.extensions.lang

/**
 * Alias of [forEach] with the item as the receiver.
 */
internal inline fun <A> Sequence<A>.forEachRun(block: A.() -> Unit) {
    forEach(block)
}

/**
 * Alias of [map] with the item as the receiver.
 */
internal fun <A, R> Sequence<A>.mapRun(block: A.() -> R) = map(block)

/**
 * Filters instances of [T] as long as they pass the provided [predicate]
 */
internal inline fun <reified T> Sequence<*>.filterIsInstance(noinline predicate: T.() -> Boolean): Sequence<T> =
    filterIsInstance<T>().filter(predicate)
