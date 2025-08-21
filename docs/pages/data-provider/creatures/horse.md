---
title: horse
---

## `Faker().horse`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/horse.yml:horse_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().horse.name() // => Ebony
    Faker().horse.breed() // => Abaco Barb
    ```
