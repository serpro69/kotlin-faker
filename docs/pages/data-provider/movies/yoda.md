---
title: yoda
faker: movies
---

## `Faker().yoda`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/yoda.yml:yoda_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().yoda.quotes() // => Use your feelings, Obi-Wan, and find him you will.
    ```
