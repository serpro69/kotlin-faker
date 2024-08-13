package io.github.serpro69.kfaker.kotest

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary

/**
 * Creates a new [Arb] that generates values from the given [block] function.
 *
 * Example for fake generators:
 * ```
 * val f = Faker()
 * forAll(Arb.of(f.address::city)) { c -> c.isNotBlank() }
 * ```
 *
 * Example using random class instances:
 * ```
 * val f = Faker()
 * forAll(Arb.of(f.randomClass::randomClassInstance)) { foo: Foo -> foo.bar.s.isNotBlank() }
 * ```
 */
fun <T> Arb.Companion.of(block: () -> T) = arbitrary { block() }
