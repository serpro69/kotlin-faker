---
---

# Faker DSL

Faker comes with a DSL to create `Faker` and `FakerConfig` instances:

{% tabs %}

{% fakerdsl "Faker DSL" %} {% filter compileAs('md') %}

❶ Use the `faker` dsl function to create an instance of `Faker`.

❷ Inside the `faker` dsl function use `fakerConfig` function to configure this instance of `Faker`.

```kotlin
val faker = faker { // ❶
    fakerConfig { // ❷
        locale = "nl"
        random = Random(42)
        uniqueGeneratorRetryLimit = 111
    }
}
```

{% endfilter %} {% endfakerdsl %}

{% configdsl "Config Builder" %} {% filter compileAs('md') %}

❶ Alternatively create `FakerConfig` instance with the top-level `fakerConfig` function.

❷ Create the `Faker` instance with custom configuration.

```kotlin
val config = fakerConfig { // ❶
    locale = "nl"
    uniqueGeneratorRetryLimit = 111
    random = if (theAnswerToTheUltimateQuestion) Random(42) else Random()
}

val faker = Faker(config) // ❷
```

{% endfilter %} {% endconfigdsl %}

{% traditional "Non-DSL" %} {% filter compileAs('md') %}

❶ Create `FakerConfig.Builder` instance and postpone instantiation of `FakerConfig`.

❷ Add extra configuration later on.

❸ Build the `FakerConfig` when ready.

❹ Create the `Faker` instance with custom configuration

```kotlin
val configBuilder = FakerConfig.Builder() // ❶
    .setLocale("nl")
    .setUniqueGeneratorRetryLimit(111)

configBuilder.setRandomSeed(42) // ❷

val config: FakerConfig = configBuilder.build() // ❸

val faker = Faker(config) // ❹
```

{% endfilter %} {% endtraditional %}

{% endtabs %}
