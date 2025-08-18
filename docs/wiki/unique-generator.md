---
icon: material/unicorn-variant
---

# :material-unicorn-variant: Generator of Unique Values

<strong>kotlin-faker</strong> supports generation of unique (non-repeatable) values and there are two ways to use this: _"globally"_ - per data-provider, and _"locally"_ - per function.

## Unique Values for Entire Data Provider

❶ Use the `faker.unique.configuration` function to configure unique generation of values "globally".

❷ Enable generation of unique values for `Address` data provider

❸ Each invocation of `faker.address.<someFun>()` will generate a unique value.

❹ Repeated invocations will produce unique values until all the values are exhausted and a `uniqueGeneratorRetryLimit` is reached.

=== "kotlin"
    ```kotlin
    --8<-- "UniqueGenerator.kt:unique_data_generator_one"
    ```

=== "java"
    ```java
    ```

<!-- TODO: --8<-- "UniqueGeneratorJ.java:unique_data_generator_one_java" -->

!!! warning
    Unique values can be exhausted and <code>Faker</code> will throw a <code>RetryLimitException</code> if the <code>uniqueGeneratorRetryLimit</code> is reached.
    <br>
    See also [Faker Configuration](./faker-configuration.md) page to learn how to configure <code>Faker</code>, as well as the [Configuring Retry Limit](#configuring-retry-limit) section of this page for information on retry limits for unique generators.

### Clearing Records of Generated Values

It is possible to clear (reset) the record of already generated values, so that they can be generated again, and thus minimize the chances of reaching the retry limit of generating unique values.

❶ Use the `faker.unique.clear(faker::<provider>)` function to clear the record of generated values for the particular `<provider>`, thus increasing the overall pool of possible values.

❷ Invocations of `faker.<provider>.<someFun>()` can now generate values that were already generated before.

=== "kotlin"
    ```kotlin
    --8<-- "UniqueGenerator.kt:unique_data_generator_two"
    ```

=== "java"
    ```java
    ```

<!-- TODO: --8<-- "UniqueGeneratorJ.java:unique_data_generator_two_java" -->

---

It is also possible to clear all records of generated values:

=== "kotlin"
    ```kotlin
    --8<-- "UniqueGenerator.kt:unique_data_generator_three"
    --8<-- "UniqueGenerator.kt:unique_data_generator_four"
    ```

=== "java"
    ```java
    ```

<!-- TODO: --8<-- "UniqueGeneratorJ.java:unique_data_generator_three_java" -->

### Disabling Unique Values Generator

One may want to disable generating unique values altogether. Just like clearing records of generated unique values, disabling unique generation can also be done on a provider-level:

❶ Unique generation is enabled for `Address`, `Name`, and `Internet`.

❷ Unique generation is then disabled for `Address`.

❸ `Address` does not generate unique values anymore.

❹ `Name` and `Internet` still generate unique values.


=== "kotlin"
    ```kotlin
    --8<-- "UniqueGenerator.kt:unique_data_generator_five"
    ```

=== "java"
    ```java
    ```

<!-- TODO: --8<-- "UniqueGeneratorJ.java:unique_data_generator_five_java" -->

As well as for all providers:

❶ Unique generation is disabled for `Address`, `Name`, `Internet`, and all other providers that may have been enabled.

=== "kotlin"
    ```kotlin
    --8<-- "UniqueGenerator.kt:unique_data_generator_six"
    ```

=== "java"
    ```java
    ```

<!-- TODO: --8<-- "UniqueGeneratorJ.java:unique_data_generator_six_java" -->

!!! info
    Disabling generation of unique values will effectively clear the record(s) of already generated values.

## Unique Values for a Single Function

Unique values can also be generated for a single function, instead of an entire data provider. To do so, prepend the function invocation with `unique` generator property:

<!-- TODO: --8<-- "UniqueGenerator.kt:unique_data_generator_seven" -->

=== "kotlin"
    ```kotlin
    val faker = Faker()

    repeat(10) { faker.address.unique.country() } // will generate unique country each time `country()` is prefixed with `unique`

    repeat(10) { faker.address.city() } // this will not necessarily be unique (unless `faker.unique.enable(faker::address)` was called previously)
    ```

=== "java"
    ```java
    ```

<!-- TODO: --8<-- "UniqueGeneratorJ.java:unique_data_generator_seven_java" -->

---

To clear the record of unique values that were already generated use the `clear("functionName")` with the "local" unique generator:

<!-- TODO: --8<-- "UniqueGenerator.kt:unique_data_generator_seven" -->

=== "kotlin"
    ```kotlin
    faker.address.unique.clear("city") // clears used values for `faker.address.unique.city()` function

    faker.address.unique.clearAll() // clears used values for all functions of address provider
    ```

=== "java"
    ```java
    ```

<!-- TODO: --8<-- "UniqueGenerator.kt:unique_data_generator_seven_java" -->

!!! info
    There is no <code>disable</code> function available for local unique generators - they are completely independent of the standard APIs:


=== "kotlin"
    ```kotlin
    // generates unique city each time it's called
    faker.address.unique.city()

    // generates non-unique city (unless global unique generator for 'address' is enabled)
    faker.address.city()
    ```

## Configuring retry limit

If the retry count of unique generator exceeds the configured value (defaults to `100`) then a `RetryLimitException` will be thrown.

It is possible to re-configure the default value through `FakerConfig`:

```kotlin
val config = fakerConfig {
    uniqueGeneratorRetryLimit = 1000
}

val faker = Faker(config)
```

!!! info
    Even if `uniqueGeneratorRetryLimit` is set to a very high number, one still needs to keep in mind the dataset size for a given faker function to avoid unexpected exceptions when generating unique values.
    <br>
    For example, a `city_prefix` param in the `address` category only has 7 values:

    ??? example "address.yml"
        ```yaml
        en:
          faker:
            address:
              city_prefix: [North, East, West, South, New, Lake, Port]
        # rest of the address.yml dict file
        ```

    Therefore, the initial pool size of unique values is quite small and will be exhausted very quickly, so the retry limit might need to be set to a higher than default value.
    <br>
    And obviously if one tries to generate 8 unique city prefixes a `RetryLimitException` will be thrown sooner or later, irrespective of what value is set for the `uniqueGeneratorRetryLimit` config property.
    <br>
    <br>
    A reference list of available Data Generators with their corresponding yml dictionary data can be found on the [Data Generators](./data-providers.md) wiki page.

!!! info
    It is also worth mentioning that "global" and "local" unique data generators are independent of each other, each keeping its own records of generated values that might need to be cleared eventually.
    <br>
    At the same time, the `uniqueGeneratorRetryLimit` config property applies to both equally.

## Excluding Values from Generation

Certain values can be excluded from being generated with unique generator. This is configured on the `faker` ("global") level for each (or in some cases - all) of the providers.

```kotlin
val faker = Faker()

faker.unique.configuration {
    // Enable generation of unique values for Address provider
    // Any unique generation configuration will only affect "enabled" providers
    enable(faker::address)

    // Exclude listOfValues from being generated 
    // in all providers that are enabled for unique generation
    exclude(listOfValues)

    // Exclude values starting with "A" from being generated 
    // in all providers that are enabled for unique generation
    exclude { listOf(Regex("^A")) }

    // Additional configuration for particular provider
    // First enable generation of unique values for Name provider
    enable(faker::name) {
        // Exclude listOfNames from being generated by any Name provider function
        excludeFromProvider<Name>(listOfNames)

        // Exclude listOfLastNames from being generated by Name#lastName function
        excludeFromFunction(Name::lastName, listOfLastNames)

        // Exclude values starting with "B" from being generated by any Name provider function
        excludeFromProvider<Name> { listOf(Regex("^B")) }

        // Exclude values starting with "C" from being generated by Name#country function
        excludeFromFunction(Name::lastName) { listOf(Regex("^C")) }
    }
}

// Based on the above config the following will be true in addition to generating unique values:
val city = faker.address.city()
assertTrue(listOfValues.contains(city) == false)
assertTrue(city.startsWith("A") == false)

val firstName = faker.name.firstName()
val lastName = faker.name.lastName()
assertTrue(listOfValues.contains(firstName) == false)
assertTrue(listOfValues.contains(lastName) == false)
assertTrue(listOfNames.contains(firstName) == false)
assertTrue(listOfNames.contains(lastName) == false)
assertTrue(listOfLastNames.contains(lastName) == false)
assertTrue(firstName.startsWith("A") == false)
assertTrue(lastName.startsWith("A") == false)
assertTrue(firstName.startsWith("B") == false)
assertTrue(lastName.startsWith("B") == false)
assertTrue(lastName.startsWith("C") == false)
```

!!! info
    This is only applicable when the whole category, i.e. <code>Address</code> or <code>Name</code>, is enabled for unique generation of values. Local generators will still generate unique values of their own, but won't take into consideration exclusion rules, if any are set:


=== "kotlin"
    ```kotlin
    val faker = Faker()

    faker.unique.configuration {
        enable(faker::address)
        exclude(listOfCountries)
    }

    // generates unique countries, but will never generate any of the values from the 'listOfCountries'
    faker.address.country()

    // will still generate its own unique countries, but won't consider the exclusions that are set above
    faker.address.unique.country()
    ```
