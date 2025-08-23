---
title: overwatch
faker: games
---

## `Faker().overwatch`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/overwatch.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().overwatch.heroes() // => Ana

    Faker().overwatch.locations() // => Adlersbrunn

    Faker().overwatch.quotes() // => Activating Self Destruct Sequence.
    ```
