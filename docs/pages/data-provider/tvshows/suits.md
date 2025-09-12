---
title: suits
faker: tvshows
---

## `Faker().suits`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/suits.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().suits.characters() // => Harvey Specter

    Faker().suits.quotes() // => Sometimes good is not good enough.
    ```
