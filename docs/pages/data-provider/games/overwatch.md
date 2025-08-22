---
title: overwatch
faker: games
---

## `Faker().overwatch`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/overwatch.yml:overwatch_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().overwatch.heroes() // => Ana

    Faker().overwatch.locations() // => Adlersbrunn

    Faker().overwatch.quotes() // => Activating Self Destruct Sequence.
    ```
