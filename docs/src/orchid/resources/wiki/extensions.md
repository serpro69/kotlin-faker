---
---

# Extensions

`kotlin-faker` provides "Faker extensions" for popular third-party testing-related libraries and frameworks.

{% info %}
{% filter compileAs('md') %}
The extension modules require the [main `kotlin-faker` dependency]({{ link(collectionType='wiki', collectionId='', itemId='Getting Started') }}#installing) to be on the classpath, unless otherwise specified in the given extension's documentation.
{% endfilter %}
{% endinfo %}

## ToC

- [Kotest Property](#kotest-property)

<br>

## Kotest Property

Kotlin-faker `kotest-property` and `kotest-property-ksp` artifacts provide faker-based [`Arb` generators](https://kotest.io/docs/proptest/property-test-generators.html) extensions via [KSP](https://kotlinlang.org/docs/ksp-overview.html) compiler plugin for [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html).

See the [Kotest Property Extension]({{ link(collectionType='pages', collectionId='extensions', itemId='Kotest Property Extension') }}) page for usage details.

A full working example can also be found in the [kotest-property-test](https://github.com/serpro69/kotlin-faker/tree/master/extension/kotest-property-test) project.

{% btc %}{% endbtc %}

<br>

