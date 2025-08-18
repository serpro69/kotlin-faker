---
icon: material/screwdriver
---

# :material-screwdriver: Configuring Faker

## Default Configuration

`Faker` can be configured through the `FakerConfig` class, either by passing an instance of `FakerConfig` directly, or through the `faker` builder DSL.

If no `FakerConfig` instance is passed to `Faker` constructor and configuration is not set through the `faker` builder DSL, then default configuration will be used with the following values:

- `locale == "en"`
- `random` is seeded with a pseudo-randomly generated number.
- `uniqueGeneratorRetryLimit` is set to `100`

## Deterministic Random

Faker supports seeding of it's PRNG (pseudo-random number generator) through `FakerConfig` to provide deterministic output of repeated function invocations.

=== "kotlin"
    ```kotlin
    --8<-- "core/src/integration/kotlin/io/github/serpro69/kfaker/docs/FakerConfiguration.kt:faker_config_one"
    ```
    
=== "java"
    ```java
    --8<-- "core/src/integration/java/io/github/serpro69/kfaker/docs/FakerConfigurationJ.java:faker_config_one_java"
    ```

Alternatively a `randomSeed` property can be used instead of passing an instance of `java.util.Random`:

=== "kotlin"
    ```kotlin
    --8<-- "core/src/integration/kotlin/io/github/serpro69/kfaker/docs/FakerConfiguration.kt:faker_config_two"
    ```

=== "java"
    ```java
    --8<-- "core/src/integration/java/io/github/serpro69/kfaker/docs/FakerConfigurationJ.java:faker_config_two_java"
    ```

---

!!! info
    `randomSeed` config property has precedence over `random` property, and the latter will be ignored if `randomSeed` is specified.

    === "kotlin"
        ```kotlin
        --8<-- "core/src/integration/kotlin/io/github/serpro69/kfaker/docs/FakerConfiguration.kt:faker_config_three"
        ```

    === "java"
        ```java
        --8<-- "core/src/integration/java/io/github/serpro69/kfaker/docs/FakerConfigurationJ.java:faker_config_three_java"
        ```

## Locale

By default `Faker` uses `en_US`-localized dict files to generate data:


=== "kotlin"
    ```kotlin
    --8<-- "core/src/integration/kotlin/io/github/serpro69/kfaker/docs/FakerConfiguration.kt:faker_config_four"
    ```

When needed, `Faker` can be configured to use a custom localized dictionary file instead:


=== "kotlin"
    ```kotlin
    --8<-- "core/src/integration/kotlin/io/github/serpro69/kfaker/docs/FakerConfiguration.kt:faker_config_five"
    ```

=== "java"
    ```java
    --8<-- "core/src/integration/java/io/github/serpro69/kfaker/docs/FakerConfigurationJ.java:faker_config_five_java"
    ```

!!! info
    The `locale` configuration property should be set following the `.yml` dict file name, that is a `-` should be used as a delimiter between "language" and "country" values instead of `_`, i.e. `nb-NO` instead of `nb_NO`.

---

Using a non-default locale will replace the values in _some_ of the providers with the values from localized dictionary.

=== "kotlin"
    ```kotlin
    val config = fakerConfig { locale = "es" }
    val faker = Faker(config)
    faker.address.city() // => Barcelona
    ```

!!! info
    Note that if the localized dictionary file does not contain a category (or a parameter in a category) that is present in the default locale, then non-localized (`en`) value will be generated instead.

    === "kotlin"
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

---

!!! info
    A list of all locales, and their corresponding dictionary files, can be found on the [Available Locales](./available-locales.md) page.

## Random Class Instance

[RandomClassInstance](./extras.md#random-class-instance) can also be configured from the `FakerConfig`.

!!! info
    This configuration takes the least precedence and will be overridden by config set on the `faker.randomProvider` level or on the `randomClassInstance` function level.
    <br>
    See [Random Class Instance Configuration](./extras.md#random-class-instance-configuration) for more details. 
