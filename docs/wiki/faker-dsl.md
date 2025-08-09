---
---

# Faker DSL

Faker comes with a DSL to create `Faker` and `FakerConfig` instances.

If you're using kotlin - chances are, you will want to use a DSL for creating and configuring `Faker` instances. An exception to this could be if you want to postpone creating the `FakerConfig` instance to a later point, in which case check out the "Non-DSL" way of configuring `Faker`s.



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





---

{% tip %}
{% filter compileAs('html') %}
<p class="description-text">
If you're interested in using the DSL from Java, check out the <a href="{{ link(collectionType='wiki', collectionId='', itemId='Java Interop') }}#using-faker-dsl">Java Interop - Using Faker DSL</a> for more details, else go to the {{ anchor(title='Faker Configuration', collectionType='wiki', collectionId='', itemId='Faker Configuration') }} page that also describes how to configure <code>Faker</code> in a more "traditional-java-way".
</p>

{% endtip %}
