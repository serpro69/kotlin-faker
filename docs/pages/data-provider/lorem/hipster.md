---
title: hipster
---

## `Faker().hipster`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/hipster.yml:hipster_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hipster.words() // => Wes Anderson
    ```
