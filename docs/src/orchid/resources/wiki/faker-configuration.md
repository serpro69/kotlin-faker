---
---

# Faker Configuration

## ToC

* [Default Configuration](#default-configuration)
* [Deterministic Random](#deterministic-random)
* [Locale](#locale)

<br>

## Default Configuration

`Faker` can be configured through the `FakerConfig` class, either by passing an instance of `FakerConfig` directly, or  

If no `FakerConfig` instance is passed to `Faker` constructor and configuration is not set through the `faker` builder DSL, then default configuration will be used:

- `locale == "en"`
- `random` is seeded with a pseudo-randomly generated number.
- `uniqueGeneratorRetryLimit` is set to `100`

{% btc %}{% endbtc %}

<br>

## Deterministic Random

Faker supports seeding of it's PRNG (pseudo-random number generator) through `FakerConfig` to provide deterministic output of repeated function invocations.

{% tabs %}

{% kotlin %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'faker_config_one' %}
```
{% endfilter %}
{% endkotlin %}

{% java %}
{% filter compileAs('md') %}
```java
{% snippet 'faker_config_one_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

<br>

Alternatively a `randomSeed` property can be used instead of passing an instance of `java.util.Random`:

{% tabs %}

{% kotlin %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'faker_config_two' %}
```
{% endfilter %}
{% endkotlin %}

{% java %}
{% filter compileAs('md') %}
```java
{% snippet 'faker_config_two_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

---

{% info %}
{% filter compileAs('md') %}
`randomSeed` config property has precedence over `random` property, and the latter will be ignored if `randomSeed` is specified.
{% endfilter %}
{% endinfo %}

{% tabs %}

{% kotlin %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'faker_config_three' %}
```
{% endfilter %}
{% endkotlin %}

{% java %}
{% filter compileAs('md') %}
```java
{% snippet 'faker_config_three_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

{% btc %}{% endbtc %}

<br>

## Locale

By default `Faker` uses `en_US`-localized dict files to generate data:

{% tabs %}
{% kotlin %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'faker_config_four' %}
```
{% endfilter %}
{% endkotlin %}
{% endtabs %}

<br>

When needed, `Faker` can be configured to use a custom localized dictionary file instead:

{% tabs %}

{% kotlin %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'faker_config_five' %}
```
{% endfilter %}
{% endkotlin %}

{% java %}
{% filter compileAs('md') %}
```java
{% snippet 'faker_config_five_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

{% info %}
{% filter compileAs('md') %}
The `locale` configuration property should be set following the `.yml` dict file name, that is a `-` should be used as a delimiter between "language" and "country" values instead of `_`, i.e. `nb-NO` instead of `nb_NO`.
{% endfilter %}
{% endinfo %}

<br>

---

<br>

Using a non-default locale will replace the values in _some_ of the providers with the values from localized dictionary.

{% tabs %}
{% kotlin %}
{% filter compileAs('md') %}
```kotlin
val config = fakerConfig { locale = "es" }
val faker = Faker(config)
faker.address.city() // => Barcelona
```
{% endfilter %}
{% endkotlin %}
{% endtabs %}

<br>

{% info %}
Note that if the localized dictionary file does not contain a category (or a parameter in a category)
that is present in the default locale, then non-localized (`en`) value will be generated instead.
{% tabs %}
{% kotlin %}
{% filter compileAs('md') %}
```kotlin
val faker = Faker() // uses default 'en' locale
faker.gameOfThrones.cities() // => Braavos

val config = fakerConfig {
    locale = "nb-NO"
}
val localizedFaker = Faker(config)
// 'game_of_thrones' category is not localized for `nb-NO` locale
// and non-localized value is returned instead
localizedFaker.gameOfThrones.cities() // => Braavos
```
{% endfilter %}
{% endkotlin %}
{% endtabs %}
{% endinfo %}

---

<br>

{% box %}
A list of all locales, and their corresponding dictionary files, can be found on the {{ anchor(title='Available Locales', collectionType='wiki', collectionId='', itemId='Available Locales') }} page.
{% endbox %}

{% btc %}{% endbtc %}

<br>
