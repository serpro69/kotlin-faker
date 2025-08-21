---
title: cat
---

## `Faker().cat`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/cat.yml:cat_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().cat.name() // => Alfie

    Faker().cat.breed() // => Abyssinian

    Faker().cat.registry() // => American Cat Fanciers Association
    ```
