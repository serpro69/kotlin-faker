---
title: adjective
---

## `Faker().adjective`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/adjective.yml:adjective_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().adjective.positive() // => adorable

    Faker().adjective.negative() // => aggressive
    ```
