---
icon: material/code-block-braces
---

# :material-code-block-braces: Faker DSL

Faker comes with a DSL to create `Faker` and `FakerConfig` instances.

If you're using kotlin - chances are, you will want to use a DSL for creating and configuring `Faker` instances. An exception to this could be if you want to postpone creating the `FakerConfig` instance to a later point, in which case check out the "Non-DSL" way of configuring `Faker`s.

=== "Faker DSL :material-language-kotlin:"

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

=== "Config Builder :material-language-kotlin:"

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

=== "Non-DSL :material-language-kotlin:"

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

!!! tip
    If you're interested in using the DSL from Java, check out the [Java Interop - Using Faker DSL](./java-interop.md#using-faker-dsl) for more details.
    <br>
    Also take a look at the [Faker Configuration](./faker-configuration.md) page which also describes how to configure `Faker` in a more "traditional-java-way".
