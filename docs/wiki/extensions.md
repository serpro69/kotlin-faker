---
icon: material/hand-extended
---

# :material-hand-extended: Extensions

`kotlin-faker` provides "Faker extensions" for popular third-party testing-related libraries and frameworks.

!!! info
    The extension modules require the [main `kotlin-faker` dependency](./getting-started.md#installing) to be on the classpath, unless otherwise specified in the given extension's documentation.

## BLNS

Kotlin-faker `blns` artifact provides convenience functions for returning strings from [The Big List of Naughty Strings](https://github.com/minimaxir/big-list-of-naughty-strings) - a list of strings which have a high probability of causing issues when used as user-input data, and can therefore be quite useful in testing.

See the [Big List of Naughty Strings Extension](../pages/extensions/blns-extension.md) page for usage details.

## Kotest Property

`kotlin-faker-kotest-property` artifact extends [`Arb` generators](https://kotest.io/docs/proptest/property-test-generators.html) and provides an easy way to use kotlin-faker functionality with [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html).

See the [Kotest Property Extension](../pages/extensions/kotest-property-extension.md) page for usage details.
