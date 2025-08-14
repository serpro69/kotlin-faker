---
icon: material/hand-extended
---

# :material-hand-extended: Extensions

`kotlin-faker` provides "Faker extensions" for popular third-party testing-related libraries and frameworks.

{% info %}

The extension modules require the [main `kotlin-faker` dependency]({{ link(collectionType='wiki', collectionId='', itemId='Getting Started') }}#installing) to be on the classpath, unless otherwise specified in the given extension's documentation.

{% endinfo %}

<br>

## BLNS

Kotlin-faker `blns` artifact provides convenience functions for returning strings from [The Big List of Naughty Strings](https://github.com/minimaxir/big-list-of-naughty-strings) - a list of strings which have a high probability of causing issues when used as user-input data, and can therefore be quite useful in testing.

See the [Big List of Naughty Strings Extension]({{ link(collectionType='pages', collectionId='extensions', itemId='Blns Extension') }}) page for usage details.



<br>

## Kotest Property

`kotlin-faker-kotest-property` artifact extends [`Arb` generators](https://kotest.io/docs/proptest/property-test-generators.html) and provides an easy way to use kotlin-faker functionality with [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html).

See the [Kotest Property Extension]({{ link(collectionType='pages', collectionId='extensions', itemId='Kotest Property Extension') }}) page for usage details.



<br>

