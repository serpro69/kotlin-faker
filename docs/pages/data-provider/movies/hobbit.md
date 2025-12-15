---
title: hobbit
faker: movies
---

## `Faker().hobbit`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/hobbit.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hobbit.character() // => Bilbo Baggins

    Faker().hobbit.thorins_company() // => Thorin Oakenshield

    Faker().hobbit.quote() // => Do you wish me a good morning, or mean that it is a good morning whether I want it or not; or that you feel good this morning; or that it is a morning to be good on?

    Faker().hobbit.location() // => Bree
    ```
