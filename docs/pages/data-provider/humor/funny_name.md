---
title: funnyName
---

## `Faker().funnyName`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/funny_name.yml:funny_name_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().funnyName.name() // => Aaron Thetires
    ```
