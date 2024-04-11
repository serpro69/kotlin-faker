# `:extension` modules

These modules provide Faker "extensions" for popular third-party testing-related libraries.

- [kotest-property](kotest-property) and [kotest-property-ksp](kotest-property-ksp) - kotlin-faker extension for [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html), provides faker-based [`Arb` generators](https://kotest.io/docs/proptest/property-test-generators.html) via [KSP](https://kotlinlang.org/docs/ksp-overview.html) compiler plugin.
  - [kotest-property-test](kotest-property-test) - example test project with additional usage details.

_NB! The extension modules require the main `kotlin-faker` dependency to be on the classpath, unless otherwise specified in the extension documentation._
