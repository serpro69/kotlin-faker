---
icon: material/ice-cream
---

# :material-ice-cream: Random Everything

Faker provides its wrapper functions around `java.util.Random` (with some additional functionality that is not covered by `java.util.Random`) through `Faker().random` property.

### Wrappers around `java.util.Random`

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_one"
    ```

### Random Enum Instance

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_two"

    --8<-- "Extras.kt:extras_random_everything_three"
    ```

### Random Strings

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_four"

    --8<-- "Extras.kt:extras_random_everything_five"
    ```

### Random sub-lists and sub-sets

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_six"

    --8<-- "Extras.kt:extras_random_everything_seven"
    ```

### Random element from a list/array

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_eight"
    ```

### Random UUID

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_nine"
    ```

### Unique Random Values

Just like most data providers, `Faker#random` supports generation of unique values. See [Generating Unique Values](./unique-generator.md) page for usage details.

Both "local" (data-generator level) and "global" (faker level) generation of unique values are supported for `RandomProvider`:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_ten"
    ```

    ```kotlin
    --8<-- "Extras.kt:extras_random_everything_eleven"
    ```
