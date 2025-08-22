---
title: animal
faker: creatures
---

## `Faker().animal`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/animal.yml:animal_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // Name
    Faker().animal.name() // => alpaca
    ```
