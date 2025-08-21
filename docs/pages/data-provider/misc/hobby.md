---
title: hobby
---

## `Faker().hobby`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/hobby.yml:hobby_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hobby.activity() // => 3D printing
    ```
