# `kotlin-faker-kotest-test`

An example project that uses [`kotest-property`](../kotest-property) and [`kotest-property-ksp`](../kotest-property-ksp) artifacts to generate faker-based [`Arb` generators](https://kotest.io/docs/proptest/property-test-generators.html) extensions via [KSP](https://kotlinlang.org/docs/ksp-overview.html) compiler plugin for [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html).

## Usage

To run tests in this module execute `./gradlew :extension:kotest-property-test:test` from the root of the kotlin-faker repository.
